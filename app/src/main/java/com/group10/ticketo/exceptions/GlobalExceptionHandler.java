package com.group10.ticketo.exceptions;

import com.group10.ticketo.dtos.CreateTicketMessageDTO;
import com.group10.ticketo.dtos.CustomerRegistrationDTO;
import com.group10.ticketo.dtos.TicketDTO;
import com.group10.ticketo.dtos.TicketMessageDTO;
import com.group10.ticketo.helpers.ViewRouteHelper;
import com.group10.ticketo.services.ITicketMessageService;
import com.group10.ticketo.services.ITicketService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Aca se  pueden manejar excepciones globales
    // Por ejemplo, capturar excepciones específicas y devolver un mensaje personalizado

    private final ITicketMessageService ticketMessageService;
    private final ITicketService ticketService;

    public GlobalExceptionHandler(ITicketMessageService ticketMessageService, ITicketService ticketService) {
        this.ticketMessageService = ticketMessageService;
        this.ticketService = ticketService;
    }

    @ExceptionHandler(UserValidationException.class)
    public String handleEmailAlreadyRegistered(UserValidationException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("customer", new CustomerRegistrationDTO());
        return ViewRouteHelper.REGISTER;
    }

    @ExceptionHandler(TicketMessageNotAllowedException.class)
    public String handleTicketMessageNotAllowed(TicketMessageNotAllowedException ex, Model model) throws Exception {
        Long ticketId = ex.getTicketId();
        List<TicketMessageDTO> messages = ticketMessageService.findByTicketId(ticketId);
        TicketDTO ticketDTO = ticketService.findById(ticketId);
        Long customerId = ticketService.findCustomerId(ticketId);
        model.addAttribute("messages", messages);
        model.addAttribute("ticket", ticketDTO);
        model.addAttribute("customerId", customerId);
        model.addAttribute("ticketMessageDTO", new CreateTicketMessageDTO());
        model.addAttribute("error", ex.getMessage());
        return ViewRouteHelper.TICKET_MESSAGES;
    }
}
