package dinosoul.snacktravelserver.global.dto;

import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class ResponseStatusDto {

    private String message;
    private Integer statusCode;

}
