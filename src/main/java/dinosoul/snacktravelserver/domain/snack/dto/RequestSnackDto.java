package dinosoul.snacktravelserver.domain.snack.dto;

import dinosoul.snacktravelserver.domain.snack.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class RequestSnackDto {
    private Category category;
    private String content;
    private String location;
}
