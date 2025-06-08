package com.group10.ticketo.controllers;

import com.group10.ticketo.dtos.TicketDTO;
import com.group10.ticketo.entities.Ticket;
import com.group10.ticketo.entities.TicketMessage;
import com.group10.ticketo.entities.User;
import com.group10.ticketo.helpers.ViewRouteHelper;
import com.group10.ticketo.repositories.ITicketStatusRepository;
import com.group10.ticketo.services.ITicketMessageService;
import com.group10.ticketo.services.ITicketService;
import com.group10.ticketo.services.ITicketStatusService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final ITicketService ticketService;
    private final ITicketMessageService ticketMessageService;
    private final ITicketStatusService ticketStatusService;

    public TicketController(ITicketService ticketService, ITicketMessageService ticketMessageService, ITicketStatusService ticketStatusService) {
        this.ticketService = ticketService;
        this.ticketMessageService = ticketMessageService;
        this.ticketStatusService = ticketStatusService;
    }

    @GetMapping("/customer")
    public String getCustomerTickets(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long customerId = user.getPerson().getId();

        List<Ticket> tickets = ticketService.findByCustomerId(customerId);

        List<TicketDTO> ticketDTOs = tickets.stream()
                .map(ticket -> new TicketDTO(
                        ticket.getId(),
                        ticket.getTitle(),
                        ticket.getCreatedAt(),
                        ticketStatusService.findByTicketIdOrderByCreatedAtDesc(ticket.getId())
                ))
                .toList();

        model.addAttribute("tickets", ticketDTOs);
        return ViewRouteHelper.TICKET_LIST;
    }

    @GetMapping("/employee")
    public String getEmployeeTickets(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long employeeId = user.getPerson().getId();
        List<Ticket> tickets = ticketMessageService.findTicketsByEmployeeId(employeeId);
        model.addAttribute("tickets", tickets);
        return ViewRouteHelper.TICKET_LIST_EMPLOYEE;
    }

    @GetMapping("/{ticketId}/messages")
    public String getTicketMessages(@PathVariable("ticketId") Long ticketId, Model model) {
        List<TicketMessage> messages = ticketMessageService.findByTicketIdOrderByCreatedAtAsc(ticketId);
        model.addAttribute("messages", messages);
        model.addAttribute("ticketId", ticketId);
        return ViewRouteHelper.TICKET_MESSAGES;
    }



}
