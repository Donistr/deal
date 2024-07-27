package org.example.deal.exception;

/**
 * Исключение сообщает о том, что не удалось найти сделку
 */
public class DealNotFoundException extends BaseException {

    public DealNotFoundException(String message) {
        super(message);
    }

}
