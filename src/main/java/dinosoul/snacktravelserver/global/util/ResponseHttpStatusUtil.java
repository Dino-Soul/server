package dinosoul.snacktravelserver.global.util;

import dinosoul.snacktravelserver.global.dto.ResponseDataDto;
import dinosoul.snacktravelserver.global.dto.ResponseStatusDto;
import dinosoul.snacktravelserver.global.globalenum.HttpStatusEnum;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class ResponseHttpStatusUtil {

    private static final ResponseHttpStatusUtil instance = new ResponseHttpStatusUtil();

    public static synchronized ResponseHttpStatusUtil getInstance() {
        return instance;
    }

    public ResponseDataDto<ResponseStatusDto> generatedResponseDto(HttpStatusEnum httpStatus) {
        ResponseStatusDto responseStatusDto = ResponseStatusDto.builder()
                .message(httpStatus.getMessage())
                .statusCode(httpStatus.getStatusCode())
                .build();

        return new ResponseDataDto<>(responseStatusDto);
    }

}