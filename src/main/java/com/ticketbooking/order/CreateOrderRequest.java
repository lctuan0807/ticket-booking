package com.ticketbooking.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateOrderRequest {
  @NotNull(message = "User id is required")
  private Long userId;

  @NotNull(message = "Ticket id is required")
  private Long ticketId;

  @NotNull(message = "Quantity is required")
  @Min(value = 1, message = "Quantity must be at least 1")
  private Integer quantity;
}
