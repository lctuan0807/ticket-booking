package com.ticketbooking.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class OrderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "BIGINT COMMENT 'Id of the user placing the order'")
  private Long userId;

  @Column(nullable = false, columnDefinition = "BIGINT COMMENT 'Ticket batch id being ordered'")
  private Long ticketId;

  @Column(nullable = false, columnDefinition = "INT COMMENT 'Number of tickets ordered'")
  private Integer quantity;

  @Column(nullable = false, columnDefinition = "DECIMAL(10,2) COMMENT 'Ticket unit price at the time of order'")
  private BigDecimal unitPrice;

  @Column(nullable = false, columnDefinition = "DECIMAL(10,2) COMMENT 'Total order amount (unitPrice * quantity)'")
  private BigDecimal totalAmount;

  @Column(nullable = false, columnDefinition = "INT DEFAULT 0 COMMENT 'Order status: 0=CONFIRMED, 1=CANCELLED, 2=FAILED'")
  private int status;

  @Column(nullable = false, columnDefinition = "DATETIME COMMENT 'Created at'")
  private LocalDateTime createdAt;

  @Column(nullable = false, columnDefinition = "DATETIME COMMENT 'Updated at'")
  private LocalDateTime updatedAt;
}
