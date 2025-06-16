package com.group10.ticketo.controllers;

import com.group10.ticketo.dtos.StatusDTO;
import com.group10.ticketo.entities.Status;
import com.group10.ticketo.services.implementation.StatusService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/status")
public class ApiStatusController {
    private final StatusService statusService;

    public ApiStatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Status>> getAllStatusses() {
        List<Status> statuses = statusService.getAllStatuses();
        if (statuses.isEmpty()) return ResponseEntity.noContent().build();//204
        return ResponseEntity.ok(statuses);//200
    }

    @PostMapping("create")
    public ResponseEntity<Status>createStatus(@RequestBody @Valid StatusDTO dto){
        Status status=statusService.createStatus(dto);
        return ResponseEntity.ok(status);
    }
}