package com.ticketbooking.ticketitem;

public class TicketItemNotFoundException extends RuntimeException {
  public TicketItemNotFoundException(Long id) {
    super("TicketItem not found with id: " + id);
  }
}
