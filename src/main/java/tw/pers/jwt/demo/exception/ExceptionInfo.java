package tw.pers.jwt.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionInfo {

    private ErrorCode errorCode;
    private String message;

}
