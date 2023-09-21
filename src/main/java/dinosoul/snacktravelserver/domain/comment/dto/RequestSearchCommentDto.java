package dinosoul.snacktravelserver.domain.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class RequestSearchCommentDto {
    private Long snackId;
}
