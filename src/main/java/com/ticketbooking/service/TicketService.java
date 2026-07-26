package com.ticketbooking.service;

import java.util.List;

import com.ticketbooking.dto.CreateTicketRequest;
import com.ticketbooking.dto.TicketDTO;

public interface TicketService {
  TicketDTO createTicket(CreateTicketRequest request);

  TicketDTO getTicket(Long id);

  List<TicketDTO> listTickets();

  TicketDTO updateTicket(Long id, CreateTicketRequest request);

  void deleteTicket(Long id);
}
