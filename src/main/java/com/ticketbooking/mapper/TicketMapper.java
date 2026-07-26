package com.ticketbooking.mapper;

import com.ticketbooking.dto.TicketDTO;
import com.ticketbooking.entity.TicketEntity;
import com.ticketbooking.enums.TicketStatusEnum;

public class TicketMapper {
  public static TicketDTO toDTO(TicketEntity entity) {
    TicketDTO dto = new TicketDTO();
    dto.setId(entity.getId());
    dto.setMatchId(entity.getMatchId());
    dto.setSeatCategory(entity.getSeatCategory());
    dto.setPrice(entity.getPrice());
    dto.setDescription(entity.getDescription());
    dto.setTotalQuantity(entity.getTotalQuantity());
    dto.setSoldQuantity(entity.getSoldQuantity());
    dto.setSaleStartAt(entity.getSaleStartAt());
    dto.setSaleEndAt(entity.getSaleEndAt());
    dto.setStatus(TicketStatusEnum.fromInt(entity.getStatus()));
    return dto;
  }
}
