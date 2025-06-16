package com.group10.ticketo.controllers;

import com.group10.ticketo.dtos.CreateTicketDTO;
import com.group10.ticketo.dtos.TicketDTO;
import com.group10.ticketo.entities.Employee;
import com.group10.ticketo.entities.Person;
import com.group10.ticketo.entities.User;
import com.group10.ticketo.services.ITicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class ApiTicketController {

    private final ITicketService ticketService;

    public ApiTicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }


    @PostMapping
    public ResponseEntity<String> createTicket(@RequestBody @Valid CreateTicketDTO dto) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setCustomerId(user.getPerson().getId());

        ticketService.createTicket(dto);
        return ResponseEntity.created(URI.create("/api/v1/tickets/my")).body("Ticket creado correctamente.");
    }

    @GetMapping("/department")
    public ResponseEntity<?> getDepartmentTickets() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = user.getPerson();

        if (person instanceof Employee employee) {
            Long departmentId = employee.getDepartment().getId();
            List<TicketDTO> tickets = ticketService.findTicketsByDepartmentId(departmentId);
            return ResponseEntity.ok(tickets);
        }

        return ResponseEntity.status(403).body("No autorizado: solo empleados pueden ver tickets de su departamento.");
    }
}
