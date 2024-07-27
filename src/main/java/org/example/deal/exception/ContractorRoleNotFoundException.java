package org.example.deal.exception;

/**
 * Исключение сообщает о том, что не удалось найти роль контрагента
 */
public class ContractorRoleNotFoundException extends BaseException {

    public ContractorRoleNotFoundException(String message) {
        super(message);
    }

}
