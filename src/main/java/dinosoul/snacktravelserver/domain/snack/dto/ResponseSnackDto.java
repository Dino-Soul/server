package dinosoul.snacktravelserver.domain.snack.dto;

import dinosoul.snacktravelserver.domain.snack.entity.Snack;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class ResponseSnackDto {
    private Long snackId;
    private String content;
    private String videoUrl;
    private String nickname;
//    private Long commentCount;
//    private Long likeCount;
//    private Boolean isLike;

    public ResponseSnackDto(Snack snack) {
        this.snackId = snack.getId();
        this.content = snack.getContent();
        this.videoUrl = snack.getVideoUrl();
        this.nickname = snack.getMember().getNickname();
//        this.commentCount = commentCount;
//        this.likeCount = likeCount;
//        this.isLike = isLike;
    }
}
