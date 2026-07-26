package com.ticketbooking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ticketbooking.enums.TicketStatusEnum;

import lombok.Data;

@Data
public class TicketDTO {
  private Long id;
  private Long matchId;
  private String seatCategory;
  private BigDecimal price;
  private String description;
  private Integer totalQuantity;
  private Integer soldQuantity;
  private LocalDateTime saleStartAt;
  private LocalDateTime saleEndAt;
  private TicketStatusEnum status;
}
