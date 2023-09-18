package dinosoul.snacktravelserver.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class RequestSignupDto {

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])[a-z0-9]{5,12}$", message = "최소 5자 이상, 12자 이하, 알파벳 소문자(a~z), 숫자(0~9)만 가능합니다.")
    private String loginId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,16}$", message = "최소 8자 이상, 16자 이하, 대소문자, 특수문자, 숫자가 각각 하나 이상 포함되어야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호를 일치시켜주세요.")
    private String passwordCheck;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

}
