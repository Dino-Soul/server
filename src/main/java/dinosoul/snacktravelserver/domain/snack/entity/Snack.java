package dinosoul.snacktravelserver.domain.snack.entity;

import dinosoul.snacktravelserver.domain.member.entity.Member;
import dinosoul.snacktravelserver.domain.snack.dto.RequestSnackDto;
import dinosoul.snacktravelserver.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Category category;

    @Column
    private String content;

    @Column
    private String location;

    @Column
    private String videoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long views;

    public Snack(RequestSnackDto requestSnackDto, String videoUrl, Member member) {
        this.category = requestSnackDto.getCategory();
        this.content = requestSnackDto.getContent();
        this.location = requestSnackDto.getLocation();
        this.videoUrl = videoUrl;
        this.member = member;
    }
}
