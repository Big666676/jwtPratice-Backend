package tw.pers.jwt.demo.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SYSTEM_ERROR("system.error"),
    USER_NOTLOGIN("user.notLogin"),
    LOGIN_FAILURE("login.failure");


    private ErrorCode(String message) {
        this.message = message;
    }

    private final String message;
    
    public String getMessage() {
        return "error." + message;
    }
}
