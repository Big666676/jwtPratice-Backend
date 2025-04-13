package tw.pers.jwt.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;
    private Object[] args;
    private HttpStatus httpStatus;

    public CustomException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(String message, ErrorCode errorCode, Object... args) {
        super(message);
        this.errorCode = errorCode;
        this.args = args;
    }


    public CustomException(String message, HttpStatus httpStatus, ErrorCode errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public CustomException(String message, HttpStatus httpStatus, ErrorCode errorCode, Object... args) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.args = args;
    }
}
