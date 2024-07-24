package org.example.deal.service;

import org.example.deal.entity.Contractor;

public interface SetMainBorrowerService {

    void setMainBorrower(Contractor contractor, boolean isMainBorrower);

    void sendMessages();

}
