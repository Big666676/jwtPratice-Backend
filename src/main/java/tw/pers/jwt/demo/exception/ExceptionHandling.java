package tw.pers.jwt.demo.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandling {

    private final MessageSource messageSource;

    @ExceptionHandler(LoginFailureException.class)
    public ResponseEntity<ExceptionInfo> handleLoginFailureException(LoginFailureException exception) {
        return getExceptionInfo(HttpStatus.UNAUTHORIZED, exception, exception.getErrorCode(), exception.getArgs());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionInfo> handleCustomException(CustomException exception) {
        return getExceptionInfo(exception.getHttpStatus(), exception, exception.getErrorCode(), exception.getArgs());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionInfo> handleException(Exception exception) {
        return getExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private ResponseEntity<ExceptionInfo> getExceptionInfo(HttpStatus httpStatus, Exception ex) {
        HttpStatus status = httpStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : httpStatus;

        return getExceptionInfo(status, ex, null, null);
    }

    private ResponseEntity<ExceptionInfo> getExceptionInfo(HttpStatus status, Exception exception, ErrorCode errorCode, Object[] arg) {
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        String errormessage = null;
        if (errorCode == null) {
            errormessage = exception.getMessage();
        } else {
            errormessage = messageSource.getMessage(errorCode.getMessage(), arg, Locale.getDefault());
        }
        exceptionInfo.setErrorCode(errorCode);
        exceptionInfo.setMessage(errormessage);
        log.error(errormessage, exception);

        return new ResponseEntity<>(exceptionInfo, status);
    }

}
