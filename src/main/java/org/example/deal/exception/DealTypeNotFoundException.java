package org.example.deal.exception;

/**
 * Исключение сообщает о том, что не удалось найти тип сделки
 */
public class DealTypeNotFoundException extends BaseException {

    public DealTypeNotFoundException(String message) {
        super(message);
    }

}
