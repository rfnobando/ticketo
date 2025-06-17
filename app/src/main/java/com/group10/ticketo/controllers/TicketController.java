package com.group10.ticketo.controllers;

import com.group10.ticketo.dtos.*;
import com.group10.ticketo.entities.*;
import com.group10.ticketo.helpers.ViewRouteHelper;
import com.group10.ticketo.repositories.ITicketCategoryRepository;
import com.group10.ticketo.repositories.ITicketRepository;
import com.group10.ticketo.services.ITicketMessageService;
import com.group10.ticketo.services.ITicketService;
import com.group10.ticketo.services.implementation.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final ITicketService ticketService;
    private final ITicketMessageService ticketMessageService;
    private final ITicketCategoryRepository ticketCategoryRepository;
    private final ITicketRepository ticketRepository;

    public TicketController(ITicketService ticketService, ITicketMessageService ticketMessageService,
                            ITicketCategoryRepository ticketCategoryRepository, ITicketRepository ticketRepository) {
        this.ticketService = ticketService;
        this.ticketMessageService = ticketMessageService;
        this.ticketCategoryRepository = ticketCategoryRepository;
        this.ticketRepository = ticketRepository;
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/myTickets")
    public String getCustomerTickets(@RequestParam(name = "estado", required = false) String state,
                                     @RequestParam(name = "orden", required = false, defaultValue = "asc") String order,
                                     Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long customerId = user.getPerson().getId();

        List<TicketDTO> ticketsDTO = ticketService.findByCustomerIdAndFilters(customerId, state, order);

        model.addAttribute("tickets", ticketsDTO);
        model.addAttribute("tituloPagina", "Mis Tickets");
        model.addAttribute("mensajeVacio", "No tenés tickets registrados.");
        model.addAttribute("mostrarBotonCrear", true);
        model.addAttribute("linkAlternativo", null);
        model.addAttribute("actionUrl", "/tickets/myTickets");
        model.addAttribute("estadoFiltro", state);
        model.addAttribute("ordenFiltro", order);
        return ViewRouteHelper.TICKET_LIST;
    }

    @GetMapping("/{ticketId}/messages")
    public String getTicketMessages(@PathVariable("ticketId") Long ticketId,
                                    HttpServletRequest request,
                                    HttpSession session,
                                    Model model) throws Exception {
        List<TicketMessageDTO> messages = ticketMessageService.findByTicketId(ticketId);
        TicketDTO ticketDTO = ticketService.findById(ticketId);
        Long customerId = ticketService.findCustomerId(ticketId);

        Ticket ticket = ticketService.findByIdTicket(ticketId);


        String referer = request.getHeader("Referer");
        if (referer != null && !referer.contains("/tickets/" + ticketId + "/messages")) {
            session.setAttribute("lastTicketPage", referer);
        }


        model.addAttribute("messages", messages);
        model.addAttribute("ticket", ticketDTO);
        model.addAttribute("customerId", customerId);
        model.addAttribute("ticketMessageDTO", new CreateTicketMessageDTO());
        model.addAttribute("customerName", ticket.getCustomer().getName());
        return ViewRouteHelper.TICKET_MESSAGES;
    }

    @PostMapping("/{ticketId}/messages")
    public String createTicketMessage(@PathVariable("ticketId") Long ticketId, @ModelAttribute("ticketMessageDTO") @Valid CreateTicketMessageDTO dto,
                                      BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            model.addAttribute("categories", ticketCategoryRepository.findAll());
            return ViewRouteHelper.TICKET_CREATE_TICKET;
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setPersonId(user.getPerson().getId());
        dto.setTicketId(ticketId);

        ticketMessageService.createTicketMessage(dto);

        return "redirect:/tickets/{ticketId}/messages";
    }

    @PreAuthorize("hasAuthority('CREATE_TICKET')")
    @GetMapping("/create")
    public String showCreateTicketForm(Model model) {
        model.addAttribute("ticketDTO", new CreateTicketDTO());
        model.addAttribute("categories", ticketCategoryRepository.findAll());
        return ViewRouteHelper.TICKET_CREATE_TICKET;
    }

    @PostMapping("/create")
    public String createTicket(@ModelAttribute("ticketDTO") @Valid CreateTicketDTO dto,
                               BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            model.addAttribute("categories", ticketCategoryRepository.findAll());
            return ViewRouteHelper.TICKET_CREATE_TICKET;
        }

        // Obtenemos el ID del cliente desde la sesión
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setCustomerId(user.getPerson().getId());

        ticketService.createTicket(dto);
        return "redirect:/tickets/myTickets?success=true";
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping("/myDepartamentTickets")
    public String getDepartmentTickets(@RequestParam(name = "estado", required = false) String state,
                                       @RequestParam(name = "orden", required = false, defaultValue = "asc") String order,
                                       Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = user.getPerson();

        if (person instanceof Employee employee) {
            Long departmentId = employee.getDepartment().getId();
            List<TicketDTO> tickets = ticketService.findTicketsByDepartmentIdAndFilters(departmentId, state, order);

            model.addAttribute("tickets", tickets);
            model.addAttribute("tituloPagina", "Tickets del Departamento");
            model.addAttribute("mensajeVacio", "No hay tickets registrados para tu departamento.");
            model.addAttribute("mostrarBotonCrear", false);
            model.addAttribute("linkAlternativo", Map.of("url", "/tickets/answered", "texto", "Ver tickets contestados"));
            model.addAttribute("actionUrl", "/tickets/myDepartamentTickets");
            model.addAttribute("estadoFiltro", state);
            model.addAttribute("ordenFiltro", order);

            return ViewRouteHelper.TICKET_LIST;
        }

        return "redirect:/?error=not_authorized";
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping("/answered")
    public String getAnsweredTickets(@RequestParam(name = "estado", required = false) String state,
                                     @RequestParam(name = "orden", required = false, defaultValue = "asc") String order,
                                     Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = user.getPerson();

        if (person instanceof Employee) {
            Long employeeId = person.getId();
            List<TicketDTO> tickets = ticketService.findTicketsAnsweredByEmployeeAndFilters(employeeId, state, order);

            model.addAttribute("tickets", tickets);
            model.addAttribute("tituloPagina", "Tickets Contestados");
            model.addAttribute("mensajeVacio", "No hay tickets contestados por vos.");
            model.addAttribute("mostrarBotonCrear", false);
            model.addAttribute("linkAlternativo", Map.of("url", "/tickets/myDepartamentTickets", "texto", "Ver tickets del departamento"));
            model.addAttribute("actionUrl", "/tickets/answered");
            model.addAttribute("estadoFiltro", state);
            model.addAttribute("ordenFiltro", order);

            return ViewRouteHelper.TICKET_LIST;
        }
        return "redirect:/?error=not_authorized";
    }
}

