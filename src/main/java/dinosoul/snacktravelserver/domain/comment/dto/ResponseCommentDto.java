package dinosoul.snacktravelserver.domain.comment.dto;

import dinosoul.snacktravelserver.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class ResponseCommentDto {
    private Long id;
    private String nickname;
    private String content;
    private LocalDateTime createAt;

    public ResponseCommentDto(Comment comment) {
        this.id = comment.getId();
        this.nickname = comment.getNickname();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
    }
}
