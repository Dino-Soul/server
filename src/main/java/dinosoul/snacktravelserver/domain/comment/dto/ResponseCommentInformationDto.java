package dinosoul.snacktravelserver.domain.comment.dto;

import lombok.*;

import static lombok.AccessLevel.*;

@Builder
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class ResponseCommentInformationDto {
    private Long snackId;
    private String content;
    private String videoUrl;
    private String nickname;
    private Double latitude;
    private Double longitude;
    private String profileImageUrl;
    private String location;
}
