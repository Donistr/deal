package org.example.deal.controller;

import org.example.deal.dto.ContractorDTO;
import org.example.deal.dto.ResponseObject;
import org.example.deal.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/deal-contractor")
public class ContractorController {

    private final ContractorService contractorService;

    @Autowired
    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @PutMapping("/save")
    public ResponseEntity<ContractorDTO> createOrUpdate(@RequestBody ContractorDTO dealCreateOrUpdateDTO) {
        return ResponseEntity.ok(contractorService.createOrUpdate(dealCreateOrUpdateDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable UUID id) {
        contractorService.delete(id);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

}
