package com.ticketbooking.service;

import java.util.List;

import com.ticketbooking.dto.CreateTicketItemRequest;
import com.ticketbooking.dto.TicketItemDTO;

public interface TicketItemService {
  TicketItemDTO createTicketItem(CreateTicketItemRequest request);

  TicketItemDTO getTicketItem(Long id);

  List<TicketItemDTO> listTicketItems();

  TicketItemDTO updateTicketItem(Long id, CreateTicketItemRequest request);

  void deleteTicketItem(Long id);
}
