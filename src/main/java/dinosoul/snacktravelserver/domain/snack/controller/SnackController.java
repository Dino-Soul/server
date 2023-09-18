package dinosoul.snacktravelserver.domain.snack.controller;

import dinosoul.snacktravelserver.domain.snack.dto.RequestSnackDto;
import dinosoul.snacktravelserver.domain.snack.service.SnackService;
import dinosoul.snacktravelserver.global.dto.ResponseDataDto;
import dinosoul.snacktravelserver.global.dto.ResponseStatusDto;
import dinosoul.snacktravelserver.global.jwt.JwtUserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class SnackController {

    private final SnackService snackService;

    @PostMapping("/snack")
    public ResponseStatusDto createSnack(@RequestPart(value = "data") RequestSnackDto requestSnackDto,
                                         @RequestPart(value = "file")MultipartFile video,
                                         @AuthenticationPrincipal JwtUserDetailsImpl jwtUserDetails) {
        return snackService.createSnack(requestSnackDto, video, jwtUserDetails.getMember());
    }

    @GetMapping("/snack")
    public ResponseDataDto<?> searchSnack() {
        return snackService.searchSnack();
    }

    @GetMapping("/snack/{snackId}")
    public ResponseDataDto<?> detailSnack(@PathVariable Long snackId) {
        return snackService.detailSnack(snackId);
    }

    @DeleteMapping("/snack/{snackId}")
    public ResponseStatusDto deleteSnack(@PathVariable Long snackId,
                                         @AuthenticationPrincipal JwtUserDetailsImpl jwtUserDetails) {
        return snackService.deleteSnack(snackId, jwtUserDetails.getMember());
    }
}
