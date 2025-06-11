package com.group10.ticketo.exceptions;


import lombok.Getter;

@Getter
public class TicketMessageNotAllowedException extends RuntimeException  {
  private final Long ticketId;

  public TicketMessageNotAllowedException(String message, Long ticketId) {
    super(message);
    this.ticketId = ticketId;
  }
}
