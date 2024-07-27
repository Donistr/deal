package org.example.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.deal.dto.DealChangeStatusDTO;
import org.example.deal.dto.DealCreateOrUpdateDTO;
import org.example.deal.dto.DealDTO;
import org.example.deal.dto.DealSearchRequestDTO;
import org.example.deal.service.DealService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "api для взаимодействия со сделками")
@RestController
@RequestMapping("/deal")
public class DealController {

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @Operation(summary = "Создать/изменить сделку")
    @ApiResponse(responseCode = "200",
            description = "Созданная/измененная сделка",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DealDTO.class)) }
    )
    @PutMapping("/save")
    public ResponseEntity<DealDTO> createOrUpdate(@RequestBody DealCreateOrUpdateDTO dealCreateOrUpdateDTO) {
        return ResponseEntity.ok(dealService.createOrUpdate(dealCreateOrUpdateDTO));
    }

    @Operation(summary = "Изменить статус сделки")
    @ApiResponse(responseCode = "200",
            description = "Измененная сделка",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DealDTO.class)) }
    )
    @PatchMapping("/change/status")
    public ResponseEntity<DealDTO> changeStatus(@RequestBody DealChangeStatusDTO dealChangeStatusDTO) {
        return ResponseEntity.ok(dealService.changeStatus(dealChangeStatusDTO));
    }

    @Operation(summary = "Найти сделку по id")
    @ApiResponse(responseCode = "200",
            description = "Найденная сделка",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DealDTO.class)) }
    )
    @Parameter(description = "id сделки")
    @GetMapping("/{id}")
    public ResponseEntity<DealDTO> getDealWithContractors(@PathVariable UUID id) {
        return ResponseEntity.ok(dealService.getDealWithContractors(id));
    }

    @Operation(summary = "Получить список всех сделок, удовлетворяющих поисковому запросу")
    @ApiResponse(responseCode = "200",
            description = "Список всех сделок, удовлетворяющих поисковому запросу",
            content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = DealDTO.class))) }
    )
    @PostMapping("/search")
    public ResponseEntity<List<DealDTO>> getDealsWithFilter(@RequestBody DealSearchRequestDTO dealSearchRequestDTO, Pageable pageable) {
        return ResponseEntity.ok(dealService.getDeals(dealSearchRequestDTO, pageable));
    }

}
