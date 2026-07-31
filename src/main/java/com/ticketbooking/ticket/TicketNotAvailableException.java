package com.ticketbooking.ticket;

public class TicketNotAvailableException extends RuntimeException {
  public TicketNotAvailableException(String message) {
    super(message);
  }
}
