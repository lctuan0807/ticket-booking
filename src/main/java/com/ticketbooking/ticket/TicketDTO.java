package com.ticketbooking.ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
