package org.example.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.deal.dto.ContractorChangeRoleDTO;
import org.example.deal.dto.DealContractorRoleDTO;
import org.example.deal.dto.ResponseObject;
import org.example.deal.service.ContractorRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "api для взаимодействия с ролями контрагентов")
@RestController
@RequestMapping("/contractor-to-role")
public class ContractorRoleController {

    private final ContractorRoleService contractorRoleService;

    @Autowired
    public ContractorRoleController(ContractorRoleService contractorRoleService) {
        this.contractorRoleService = contractorRoleService;
    }

    @Operation(summary = "Добавить роль контрагенту")
    @ApiResponse(responseCode = "200",
            description = "Созданная роль контрагента",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DealContractorRoleDTO.class)) }
    )
    @PutMapping("/save")
    public ResponseEntity<DealContractorRoleDTO> createOrUpdate(@RequestBody ContractorChangeRoleDTO contractorChangeRoleDTO) {
        return ResponseEntity.ok(contractorRoleService.createOrUpdate(contractorChangeRoleDTO));
    }

    @Operation(summary = "Удалить роль контрагента")
    @ApiResponse(responseCode = "200",
            content = { @Content}
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseObject> delete(@RequestBody ContractorChangeRoleDTO contractorChangeRoleDTO) {
        contractorRoleService.delete(contractorChangeRoleDTO);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

}
