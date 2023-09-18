package dinosoul.snacktravelserver.domain.member.entity;

import dinosoul.snacktravelserver.domain.member.dto.request.RequestInformationDto;
import dinosoul.snacktravelserver.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
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

    public void update(RequestInformationDto requestInformationDto, String updateProfileImageUrl) {
        this.nickname = requestInformationDto.getNickname();
        this.password = requestInformationDto.getPassword();
        this.profileUrl = updateProfileImageUrl;
    }
}
