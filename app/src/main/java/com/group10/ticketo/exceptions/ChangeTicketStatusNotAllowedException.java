package com.group10.ticketo.exceptions;

import lombok.Getter;

@Getter
public class ChangeTicketStatusNotAllowedException extends RuntimeException {
  private final Long ticketId;

  public ChangeTicketStatusNotAllowedException(String message, Long ticketId) {
        super(message);
        this.ticketId = ticketId;
    }
}
