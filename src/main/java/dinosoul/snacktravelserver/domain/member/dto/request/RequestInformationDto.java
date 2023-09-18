package dinosoul.snacktravelserver.domain.member.dto.request;

import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class RequestInformationDto {

    private String nickname;
    private String password;
}
