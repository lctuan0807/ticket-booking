package com.ticketbooking.ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CreateTicketRequest {
  @NotNull(message = "Match id is required")
  private Long matchId;

  @NotBlank(message = "Seat category cannot be empty")
  @Size(max = 50, message = "Seat category cannot exceed 50 characters")
  private String seatCategory;

  @NotNull(message = "Price is required")
  private BigDecimal price;

  @Size(max = 500, message = "Description cannot exceed 500 characters")
  private String description;

  @NotNull(message = "Total quantity is required")
  private Integer totalQuantity;

  private LocalDateTime saleStartAt;

  private LocalDateTime saleEndAt;
}
