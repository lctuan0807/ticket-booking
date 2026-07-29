package com.ticketbooking.ticketitem;

public class TicketItemMapper {
  public static TicketItemDTO toDTO(TicketItemEntity entity) {
    TicketItemDTO dto = new TicketItemDTO();
    dto.setId(entity.getId());
    dto.setTicketId(entity.getTicketId());
    dto.setTicketCode(entity.getTicketCode());
    dto.setSeatSection(entity.getSeatSection());
    dto.setSeatRow(entity.getSeatRow());
    dto.setSeatNumber(entity.getSeatNumber());
    dto.setStatus(TicketItemStatusEnum.fromInt(entity.getStatus()));
    return dto;
  }
}
