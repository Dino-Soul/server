package dinosoul.snacktravelserver.domain.snack.entity;

import dinosoul.snacktravelserver.domain.comment.entity.Comment;
import dinosoul.snacktravelserver.domain.member.entity.Member;
import dinosoul.snacktravelserver.domain.snack.dto.RequestSnackDto;
import dinosoul.snacktravelserver.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Snack extends Timestamped {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "snack_id")
    private Long id;

    @Column
    private String content;

    @Column
    private String location;

    @Column
    private String videoUrl;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    @JoinColumn(name = "comment_id")
    List<Comment> commentList = new ArrayList<>();

    public Snack(RequestSnackDto requestSnackDto, String videoUrl, Member member) {
        this.content = requestSnackDto.getContent();
        this.location = requestSnackDto.getLocation();
        this.videoUrl = videoUrl;
        this.member = member;
    }
}
