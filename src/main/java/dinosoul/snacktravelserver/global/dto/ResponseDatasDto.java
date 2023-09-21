package dinosoul.snacktravelserver.global.dto;

import dinosoul.snacktravelserver.domain.comment.dto.ResponseCommentInformationDto;
import lombok.Getter;

@Getter
public class ResponseDatasDto<T> {

    private T data;
    private ResponseCommentInformationDto snackInfo;

    public ResponseDatasDto(T data, ResponseCommentInformationDto responseCommentInformationDto) {
        this.data = data;
        this.snackInfo = responseCommentInformationDto;
    }
}