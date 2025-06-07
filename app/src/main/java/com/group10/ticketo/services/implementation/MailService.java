package com.group10.ticketo.services.implementation;

import com.group10.ticketo.entities.*;
import com.group10.ticketo.services.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MailService implements IMailService {

    private final JavaMailSender javaMailSender;

    @Value("${EMAIL}")
    private String defaultFrom;
    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(String to, String subject, String body)throws Exception {
        if(to==null || to.isEmpty()) throw new Exception("Error:Recipient email cannot be null or empty.");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(defaultFrom);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new Exception("Error sending email: " + e.getMessage(), e);
        }
    }
    @Override
    public void sendTicketMessageToClient(TicketMessage ticketMessage) throws Exception{
        if (ticketMessage == null) throw new Exception("Error: TicketMessage is null");

        Person author = ticketMessage.getPerson();

        if(author == null) throw new Exception("Error: The author is null");
        if(!(author instanceof Employee)) throw new Exception("Error: The person is not an Employee");

        Ticket ticket = ticketMessage.getTicket();
        if(ticket == null) throw new Exception("Error: The ticket is null");

        Customer customer = ticket.getCustomer();
        if(customer == null) throw new Exception("Error: The customer is null");

        User user = customer.getUser();
        if(user == null) throw new Exception("Error: The user is null");

        if (user.getEmail() == null || user.getEmail().isEmpty()) throw new Exception("Error: The customer's email is invalid or empty.");

        String subject = "New response in your ticket: " + ticket.getTitle();
        String body = "Hello " + customer.getName() + ",\n\n"
                + "You have a new message in your ticket:\n\n"
                + ticketMessage.getBody() + "\n\n"
                + "Best regards,\nThe Support Team of Ticketo";


        sendMail(user.getEmail(), subject, body);


    }
}
