package dinosoul.snacktravelserver.domain.member.service;

import dinosoul.snacktravelserver.domain.member.dto.request.RequestSignupDto;
import dinosoul.snacktravelserver.domain.member.repository.MemberRepository;
import dinosoul.snacktravelserver.global.dto.ResponseDataDto;
import dinosoul.snacktravelserver.global.dto.ResponseStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public ResponseDataDto<ResponseStatusDto> signup(RequestSignupDto requestSignupDto) {
        return null;
    }

}
