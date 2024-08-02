package org.example.deal.quartz;

import org.example.deal.dto.SetMainBorrowerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "contractor", url = "${contractor.url}")
public interface ContractorServiceClient {

    @PatchMapping("/contractor/main-borrower")
    ResponseEntity<Void> setMainBorrower(@RequestBody SetMainBorrowerDTO setMainBorrowerDTO);

}
