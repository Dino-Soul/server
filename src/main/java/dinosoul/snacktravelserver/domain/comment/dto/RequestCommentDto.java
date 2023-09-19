package dinosoul.snacktravelserver.domain.comment.dto;

import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class RequestCommentDto {
    private String comment;
}
