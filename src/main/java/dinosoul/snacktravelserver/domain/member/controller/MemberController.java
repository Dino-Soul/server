package dinosoul.snacktravelserver.domain.member.controller;

import dinosoul.snacktravelserver.domain.member.dto.request.RequestInformationDto;
import dinosoul.snacktravelserver.domain.member.dto.request.RequestSignupDto;
import dinosoul.snacktravelserver.domain.member.service.MemberService;
import dinosoul.snacktravelserver.global.dto.ResponseStatusDto;
import dinosoul.snacktravelserver.global.jwt.JwtUserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/auth/signup")
    public ResponseStatusDto signup(@RequestBody RequestSignupDto requestSignupDto) {
        return memberService.signup(requestSignupDto);
    }

    @PutMapping("auth/information")
    public ResponseStatusDto informationChange(@RequestPart(value = "data") RequestInformationDto requestInformationDto,
                                               @RequestPart(value = "file") MultipartFile profileImage,
                                               @AuthenticationPrincipal JwtUserDetailsImpl jwtUserDetails) {
        return memberService.informationChange(requestInformationDto, profileImage, jwtUserDetails.getMember());
    }
}
