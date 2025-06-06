package com.group10.ticketo.services.implementation;

import com.group10.ticketo.services.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
        if(to==null || to.isEmpty())throw new Exception("Error:Recipient email cannot be null or empty.");
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
    /*@Override
    public void sendTicketMessageToClient(TicketMessage ticketMessage) throws Exception{
        if (ticketMessage == null) throw new Exception("Error: TicketMessage is null");

        Person author = ticketMessage.getPerson();

        if(!(author instanceof Employee)){
            return;
        }
        Ticket ticket = ticketMessage.getTicket();
        Customer customer = ticket.getCustomer();

        if (customer == null || customer.getEmail() == null || customer.getEmail().isEmpty()) throw new Exception("Error: The customer's email is invalid or empty.");

        String subject = "New response in your ticket: " + ticket.getTitle();
        String body = "Hello " + customer.getFirstName() + ",\n\n"
                + "You have a new message in your ticket:\n\n"
                + ticketMessage.getBody() + "\n\n"
                + "Best regards,\nThe Support Team of Ticketo";


        sendMail(customer.getEmail(), subject, body);


    }*/
}
