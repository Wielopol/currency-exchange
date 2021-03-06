package pl.sda.transporeon.currencyexchange.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RateProcessingException.class)
    public ErrorResponse handleWeatherProcessingException(final RateProcessingException exception) {
        log.debug("Exception occured");
        return new ErrorResponse(exception.getMessage());
    }
}
