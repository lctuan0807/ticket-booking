package com.ticketbooking.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderDTO {
  private Long id;
  private Long userId;
  private Long ticketId;
  private Integer quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalAmount;
  private OrderStatusEnum status;
  private LocalDateTime createdAt;
}
