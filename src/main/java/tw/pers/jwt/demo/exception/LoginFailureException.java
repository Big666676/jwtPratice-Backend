package tw.pers.jwt.demo.exception;

import lombok.Getter;

@Getter
public class LoginFailureException extends CustomException {

    public LoginFailureException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public LoginFailureException(String message, ErrorCode errorCode, Object... args) {
        super(message, errorCode, args);
    }
}
