package dinosoul.snacktravelserver.domain.snack.service;

import dinosoul.snacktravelserver.domain.member.entity.Member;
import dinosoul.snacktravelserver.domain.snack.dto.RequestSnackDto;
import dinosoul.snacktravelserver.domain.snack.dto.ResponseSnackDto;
import dinosoul.snacktravelserver.domain.snack.entity.Snack;
import dinosoul.snacktravelserver.domain.snack.repository.SnackRepository;
import dinosoul.snacktravelserver.global.dto.ResponseDataDto;
import dinosoul.snacktravelserver.global.dto.ResponseStatusDto;
import dinosoul.snacktravelserver.global.globalenum.HttpStatusEnum;
import dinosoul.snacktravelserver.global.security.UserDetailsImpl;
import dinosoul.snacktravelserver.global.util.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static dinosoul.snacktravelserver.global.globalenum.HttpStatusEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnackService {

    private final SnackRepository snackRepository;
    private final S3Service s3Service;

    public ResponseStatusDto createSnack(RequestSnackDto requestSnackDto, MultipartFile video, Member member) {
        String videoUrl = s3Service.upload(video);
        Snack snack = new Snack(requestSnackDto, videoUrl, member);
        snackRepository.save(snack);
        return ResponseStatusDto.builder()
                .message(CREATED.getMessage())
                .statusCode(CREATED.getStatusCode())
                .build();
    }

    public ResponseDataDto<?> searchSnack() {
        List<Snack> snackList = snackRepository.findAll();
        List<ResponseSnackDto> responseSnackDtoList = snackList.stream().map(ResponseSnackDto::new).toList();
        return new ResponseDataDto<>(responseSnackDtoList);
    }

    public ResponseDataDto<?> detailSnack(Long snackId) {
        Snack findSnack = snackRepository.findById(snackId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 스낵 입니다."));
        return new ResponseDataDto<>(new ResponseSnackDto(findSnack));
    }
    @Transactional
    public ResponseStatusDto deleteSnack(Long snackId, Member member) {
        Snack findSnack = snackRepository.findById(snackId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 스낵 입니다."));
        if (!findSnack.getMember().equals(member)) {
            throw new IllegalArgumentException("스낵 삭제 권한이 없습니다.");
        }
        snackRepository.delete(findSnack);
        return ResponseStatusDto.builder().message(OK.getMessage()).statusCode(OK.getStatusCode()).build();
    }
}
