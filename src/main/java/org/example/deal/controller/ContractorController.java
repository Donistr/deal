package org.example.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.deal.dto.ContractorCreateOrUpdateDTO;
import org.example.deal.dto.ContractorDTO;
import org.example.deal.dto.ResponseObject;
import org.example.deal.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "api для взаимодействия с контрагентами")
@RestController
@RequestMapping("/deal-contractor")
public class ContractorController {

    private final ContractorService contractorService;

    @Autowired
    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @Operation(summary = "Создать/изменить контрагента", description = "Если был передан id " +
            "контрагента, который уже создан, то она будет изменен, иначе создан")
    @ApiResponse(responseCode = "200",
            description = "Созданный/изменённый контрагент",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContractorDTO.class)) }
    )
    @PutMapping("/save")
    public ResponseEntity<ContractorDTO> createOrUpdate(@RequestBody ContractorCreateOrUpdateDTO contractorCreateOrUpdateDTO) {
        return ResponseEntity.ok(contractorService.createOrUpdate(contractorCreateOrUpdateDTO));
    }

    @Operation(summary = "Удалить контрагента по id")
    @ApiResponse(responseCode = "200",
            content = { @Content}
    )
    @Parameter(description = "id контрагента")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable UUID id) {
        contractorService.delete(id);
        return ResponseEntity.ok(new ResponseObject("success"));
    }

}
