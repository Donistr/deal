package org.example.deal.exception;

/**
 * Базовый класс исключения в приложении
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

}
