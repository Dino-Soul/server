package dinosoul.snacktravelserver.domain.member.service;

import dinosoul.snacktravelserver.domain.member.dto.request.RequestInformationDto;
import dinosoul.snacktravelserver.domain.member.dto.request.RequestSignupDto;
import dinosoul.snacktravelserver.domain.member.entity.Member;
import dinosoul.snacktravelserver.domain.member.repository.MemberRepository;
import dinosoul.snacktravelserver.global.dto.ResponseDataDto;
import dinosoul.snacktravelserver.global.dto.ResponseStatusDto;
import dinosoul.snacktravelserver.global.globalenum.HttpStatusEnum;
import dinosoul.snacktravelserver.global.util.IdenticonService;
import dinosoul.snacktravelserver.global.util.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static dinosoul.snacktravelserver.global.globalenum.HttpStatusEnum.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final IdenticonService identiconService;
    private final S3Service s3Service;

    @Transactional
    public ResponseStatusDto signup(RequestSignupDto requestSignupDto) {
        if (!requestSignupDto.getPassword().equals(requestSignupDto.getPasswordCheck())) {
            throw new IllegalArgumentException("동일한 패스워드가 아닙니다.");
        }

        String defaultProfileUrl = identiconService.makeIdenticonUrl(requestSignupDto.getNickname());

        Member member = Member.builder()
                .loginId(requestSignupDto.getLoginId())
                .password(requestSignupDto.getPassword())
                .nickname(requestSignupDto.getNickname())
                .profileUrl(defaultProfileUrl)
                .build();

        memberRepository.save(member);
        return ResponseStatusDto.builder()
                .message(OK.getMessage())
                .statusCode(OK.getStatusCode())
                .build();
    }

    @Transactional
    public ResponseStatusDto informationChange(RequestInformationDto requestInformationDto,
                                               MultipartFile profileImage, Member member) {

        Member findMember = memberRepository.findById(member.getId()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 유저 입니다."));

        String findProfileImageUrl = findMember.getProfileUrl();

        if (findProfileImageUrl != null || !findProfileImageUrl.isEmpty()) {
            s3Service.delete(findProfileImageUrl);
        }

        String updateProfileImageUrl = s3Service.upload(profileImage);

        findMember.update(requestInformationDto, updateProfileImageUrl);

        return ResponseStatusDto.builder()
                .message(OK.getMessage())
                .statusCode(OK.getStatusCode())
                .build();
    }
}
