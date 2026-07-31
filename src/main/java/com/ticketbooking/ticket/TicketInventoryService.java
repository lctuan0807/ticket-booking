package com.ticketbooking.ticket;

public interface TicketInventoryService {
  TicketDTO reserveTickets(Long ticketId, int quantity);
}
