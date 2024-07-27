package org.example.deal.exception;

/**
 * Исключение сообщает о том, что не удалось найти контрагента
 */
public class ContractorNotFoundException extends BaseException {

    public ContractorNotFoundException(String message) {
        super(message);
    }

}
