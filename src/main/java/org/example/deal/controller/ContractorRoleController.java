package org.example.deal.controller;

import org.example.deal.dto.ContractorChangeRoleDTO;
import org.example.deal.dto.ResponseObject;
import org.example.deal.service.impl.ContractorRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contractor-to-role")
public class ContractorRoleController {

    private final ContractorRoleService contractorRoleService;

    @Autowired
    public ContractorRoleController(ContractorRoleService contractorRoleService) {
        this.contractorRoleService = contractorRoleService;
    }

    @PutMapping("/save")
    public ResponseEntity<ContractorChangeRoleDTO> createOrUpdate(@RequestBody ContractorChangeRoleDTO contractorChangeRoleDTO) {
        return ResponseEntity.ok(contractorRoleService.createOrUpdate(contractorChangeRoleDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseObject> delete(@RequestBody ContractorChangeRoleDTO contractorChangeRoleDTO) {
        contractorRoleService.delete(contractorChangeRoleDTO);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

}
