package org.example.deal.service;

import org.example.deal.entity.Contractor;

/**
 * Класс предоставляющий методы работы с признаком наличия сделок, где контрагент является основным заемщиком
 */
public interface SetMainBorrowerService {

    /**
     * Устанавливает признак наличия сделок, где контрагент является основным заемщиком
     * @param contractor контрагент
     * @param isMainBorrower признак наличия сделок, где контрагент является основным заемщиком
     */
    void setMainBorrower(Contractor contractor, boolean isMainBorrower);

    /**
     * Отправляет сообщение в сервис контрагентов
     */
    void sendMessages();

}
