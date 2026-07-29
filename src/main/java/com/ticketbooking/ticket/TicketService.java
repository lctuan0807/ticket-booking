package com.ticketbooking.ticket;

import java.util.List;

public interface TicketService {
  TicketDTO createTicket(CreateTicketRequest request);

  TicketDTO getTicket(Long id);

  List<TicketDTO> listTickets();

  TicketDTO updateTicket(Long id, CreateTicketRequest request);

  void deleteTicket(Long id);
}
