package dinosoul.snacktravelserver.domain.member.dto.request;

import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class RequestLoginDto {

    private String nickname;
    private String password;

}
