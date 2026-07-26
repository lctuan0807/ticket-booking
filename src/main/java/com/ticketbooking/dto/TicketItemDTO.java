package com.ticketbooking.dto;

import com.ticketbooking.enums.TicketItemStatusEnum;

import lombok.Data;

@Data
public class TicketItemDTO {
  private Long id;
  private Long ticketId;
  private String ticketCode;
  private String seatSection;
  private String seatRow;
  private String seatNumber;
  private TicketItemStatusEnum status;
}
