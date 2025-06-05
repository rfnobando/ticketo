package com.group10.ticketo.services;

public interface IMailService {
    void sendMail(String from, String to, String subject, String body);
}
