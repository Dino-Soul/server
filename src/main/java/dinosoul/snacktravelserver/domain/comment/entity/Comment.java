package dinosoul.snacktravelserver.domain.comment.entity;

import dinosoul.snacktravelserver.domain.comment.dto.RequestCommentDto;
import dinosoul.snacktravelserver.domain.member.entity.Member;
import dinosoul.snacktravelserver.domain.snack.entity.Snack;
import dinosoul.snacktravelserver.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="comment_id")
    private Long id;

    @Column
    private String nickname;

    @Column
    private String content;

    @Column(name = "snack_id")
    private Long snackId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void update(RequestCommentDto requestCommentDto) {
        this.content = requestCommentDto.getContent();
    }
}
