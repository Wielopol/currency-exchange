package pl.sda.transporeon.currencyexchange.controller.exception;

public class RateProcessingException extends RuntimeException {
    public RateProcessingException(final String message) {
        super(message);
    }
}
