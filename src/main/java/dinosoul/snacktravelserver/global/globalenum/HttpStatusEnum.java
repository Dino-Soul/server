package dinosoul.snacktravelserver.global.globalenum;

import lombok.Getter;

@Getter
public enum HttpStatusEnum {

    OK(200, "OK"),
    SUCCESS_LOGIN(200, "Success Login"),
    FAIL_LOGIN(401, "Fail Login"),
    CREATED(201, "Created"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    TOKEN_TIMEOUT(401, "Token Timeout"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");


    private final Integer statusCode;
    private final String message;

    HttpStatusEnum(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
