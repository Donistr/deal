package org.example.deal.controller;

import org.example.deal.dto.*;
import org.example.deal.service.DealService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deal")
public class DealController {

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @PutMapping("/save")
    public ResponseEntity<DealDTO> createOrUpdate(@RequestBody DealCreateOrUpdateDTO dealCreateOrUpdateDTO) {
        return ResponseEntity.ok(dealService.createOrUpdate(dealCreateOrUpdateDTO));
    }

    @PatchMapping("/change/status")
    public ResponseEntity<DealDTO> changeStatus(@RequestBody DealChangeStatusDTO dealChangeStatusDTO) {
        return ResponseEntity.ok(dealService.changeStatus(dealChangeStatusDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealDTO> getDealWithContractors(@PathVariable UUID id) {
        return ResponseEntity.ok(dealService.getDealWithContractors(id));
    }

    @PostMapping("/search")
    public ResponseEntity<List<DealDTO>> getDealsWithFilter(@RequestBody DealSearchRequestDTO dealSearchRequestDTO, Pageable pageable) {
        return ResponseEntity.ok(dealService.getDeals(dealSearchRequestDTO, pageable));
    }

}
