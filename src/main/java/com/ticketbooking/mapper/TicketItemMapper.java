package com.ticketbooking.mapper;

import com.ticketbooking.dto.TicketItemDTO;
import com.ticketbooking.entity.TicketItemEntity;
import com.ticketbooking.enums.TicketItemStatusEnum;

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
