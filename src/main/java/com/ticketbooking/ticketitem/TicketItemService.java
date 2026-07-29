package com.ticketbooking.ticketitem;

import java.util.List;

public interface TicketItemService {
  TicketItemDTO createTicketItem(CreateTicketItemRequest request);

  TicketItemDTO getTicketItem(Long id);

  List<TicketItemDTO> listTicketItems();

  TicketItemDTO updateTicketItem(Long id, CreateTicketItemRequest request);

  void deleteTicketItem(Long id);
}
