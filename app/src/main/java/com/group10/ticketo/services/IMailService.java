package com.group10.ticketo.services;

public interface IMailService {
    void sendMail(String to, String subject, String body)throws Exception;
    //void sendTicketMessageToClient(TicketMessage ticketMessage) throws Exception;
}
