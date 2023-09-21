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
    private Double latitude;
    private Double longitude;
    private String profileImageUrl;

    public ResponseSnackDto(Snack snack) {
        this.snackId = snack.getId();
        this.content = snack.getContent();
        this.videoUrl = snack.getVideoUrl();
        this.nickname = snack.getMember().getNickname();
        this.latitude = snack.getLatitude();
        this.longitude = snack.getLongitude();
        this.profileImageUrl = snack.getProfileImageUrl();
    }
}
