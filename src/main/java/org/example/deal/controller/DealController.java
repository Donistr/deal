package org.example.deal.controller;

import org.example.deal.dto.DealChangeStatusDTO;
import org.example.deal.dto.DealCreateOrUpdateDTO;
import org.example.deal.dto.DealDTO;
import org.example.deal.dto.DealWithContractorsDTO;
import org.example.deal.service.DealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(dealService.save(dealCreateOrUpdateDTO));
    }

    @PatchMapping("/change/status")
    public ResponseEntity<DealDTO> changeStatus(@RequestBody DealChangeStatusDTO dealChangeStatusDTO) {
        return ResponseEntity.ok(dealService.changeStatus(dealChangeStatusDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealWithContractorsDTO> getDealWithContractors(@PathVariable UUID id) {
        return ResponseEntity.ok(dealService.getDealWithContractors(id));
    }

}
