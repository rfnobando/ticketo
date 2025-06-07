package com.group10.ticketo.services;

import com.group10.ticketo.entities.TicketMessage;

public interface IMailService {
    void sendMail(String to, String subject, String body)throws Exception;
    void sendTicketMessageToClient(TicketMessage ticketMessage) throws Exception;
}
