package com.ticketbooking.ticket;

import java.util.List;

public interface TicketService {
  TicketDTO createTicket(CreateTicketRequest request);

  TicketDTO getTicketWithCache(Long id);

  TicketDTO getTicketWithDistributedLockCache(Long id);

  List<TicketDTO> listTickets();

  List<TicketDTO> getAvailableTickets(Long matchId);

  List<TicketDTO> getTicketsByMatchId(Long matchId, TicketStatusEnum status);

  TicketDTO updateTicket(Long id, CreateTicketRequest request);

  void deleteTicket(Long id);
}
