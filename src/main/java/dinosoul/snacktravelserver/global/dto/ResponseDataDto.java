package dinosoul.snacktravelserver.global.dto;

import lombok.*;

import static lombok.AccessLevel.*;

@Getter
public class ResponseDataDto<T> {

    private T data;

    public ResponseDataDto(T data) {
        this.data = data;
    }
}
