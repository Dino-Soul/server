package dinosoul.snacktravelserver.domain.member.entity;

import dinosoul.snacktravelserver.domain.comment.entity.Comment;
import dinosoul.snacktravelserver.domain.member.dto.request.RequestInformationDto;
import dinosoul.snacktravelserver.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Timestamped {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String profileUrl;

    @OneToMany
    @JoinColumn(name = "comment_id")
    private List<Comment> commentList = new ArrayList<>();

    public void update(String nickname, String password, String updateProfileImageUrl) {
        this.nickname = nickname;
        this.password = password;
        this.profileUrl = updateProfileImageUrl;
    }
}
