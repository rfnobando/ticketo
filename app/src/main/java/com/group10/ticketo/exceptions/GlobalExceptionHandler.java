package com.group10.ticketo.exceptions;

import com.group10.ticketo.dtos.*;
import com.group10.ticketo.entities.Ticket;
import com.group10.ticketo.helpers.ViewRouteHelper;
import com.group10.ticketo.services.ITicketMessageService;
import com.group10.ticketo.services.ITicketService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    public String handleTicketMessageNotAllowed(TicketMessageNotAllowedException ex) {
        Long ticketId = ex.getTicketId();
        String encodedMessage = URLEncoder.encode(ex.getMessage(), StandardCharsets.UTF_8);
        return "redirect:/tickets/" + ticketId + "/messages?error=" + encodedMessage;
    }
    @ExceptionHandler(ChangeTicketStatusNotAllowedException.class)
    public String handleChangeTicketStatusNotAllowedException(ChangeTicketStatusNotAllowedException ex) {
        Long ticketId = ex.getTicketId();
        String encodedMessage = URLEncoder.encode(ex.getMessage(), StandardCharsets.UTF_8);
        return "redirect:/tickets/" + ticketId + "/messages?error=" + encodedMessage;
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public String handleTicketNotFoundException(TicketNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "errors/404"; // Asegurate de tener esta vista en templates/error/404.html
    }

}
