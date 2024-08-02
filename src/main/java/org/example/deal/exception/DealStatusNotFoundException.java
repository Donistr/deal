package org.example.deal.exception;

/**
 * Исключение сообщает о том, что не удалось найти статус сделки
 */
public class DealStatusNotFoundException extends BaseException {

    public DealStatusNotFoundException(String message) {
        super(message);
    }

}
