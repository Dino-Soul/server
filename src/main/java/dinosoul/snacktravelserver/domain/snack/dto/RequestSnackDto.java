package dinosoul.snacktravelserver.domain.snack.dto;

import dinosoul.snacktravelserver.domain.snack.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestSnackDto {
    private String content;
    private String location;
}
