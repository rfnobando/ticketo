package com.group10.ticketo.controllers;

import com.group10.ticketo.dtos.CreateTicketDTO;
import com.group10.ticketo.dtos.TicketDTO;
import com.group10.ticketo.dtos.TicketMessageDTO;
import com.group10.ticketo.entities.*;
import com.group10.ticketo.helpers.ViewRouteHelper;
import com.group10.ticketo.repositories.ICustomerRepository;
import com.group10.ticketo.repositories.ITicketCategoryRepository;
import com.group10.ticketo.repositories.ITicketStatusRepository;
import com.group10.ticketo.services.ITicketMessageService;
import com.group10.ticketo.services.ITicketService;
import com.group10.ticketo.services.ITicketStatusService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final ITicketService ticketService;
    private final ITicketMessageService ticketMessageService;
    private final ITicketStatusService ticketStatusService;
    private final ICustomerRepository customerRepository;
    private final ITicketCategoryRepository ticketCategoryRepository;

    public TicketController(ITicketService ticketService, ITicketMessageService ticketMessageService, ITicketStatusService ticketStatusService,
                            ICustomerRepository customerRepository,ITicketCategoryRepository ticketCategoryRepository) {
        this.ticketService = ticketService;
        this.ticketMessageService = ticketMessageService;
        this.ticketStatusService = ticketStatusService;
        this.customerRepository = customerRepository;
        this.ticketCategoryRepository = ticketCategoryRepository;
    }

    @GetMapping("/myTickets")
    public String getCustomerTickets(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long customerId = user.getPerson().getId();

        List<TicketDTO> ticketDTOs = ticketService.findByCustomerId(customerId);

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
    public String getTicketMessages(@PathVariable("ticketId") Long ticketId, Model model) throws Exception {
        List<TicketMessageDTO> messages = ticketMessageService.findByTicketId(ticketId);
        TicketDTO ticketDTO = ticketService.findById(ticketId);
        Long customerId = ticketService.findCustomerId(ticketId);
        model.addAttribute("messages", messages);
        model.addAttribute("ticket", ticketDTO);
        model.addAttribute("customerId", customerId);
        return ViewRouteHelper.TICKET_MESSAGES;
    }

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
            model.addAttribute("categories",ticketCategoryRepository.findAll());
            return ViewRouteHelper.TICKET_CREATE_TICKET;
        }

        // Obtenemos el ID del cliente desde la sesión
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setCustomerId(user.getPerson().getId());

        ticketService.createTicket(dto);
        return "redirect:/tickets/myTickets?success=true";
    }

    @GetMapping("/myDepartamentTickets")
    public String getDepartmentTickets(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = user.getPerson();

        if (person instanceof Employee) {
            Employee employee = (Employee) person;
            Long departmentId = employee.getDepartment().getId();
            List<TicketDTO> tickets = ticketService.findTicketsByDepartmentId(departmentId);

            model.addAttribute("tickets", tickets);
            return ViewRouteHelper.TICKET_LIST_DEPARTMENT;
        }

        return "redirect:/?error=not_authorized";
    }
}
