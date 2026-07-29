package com.ticketbooking.ticket;

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
@Table(name = "tickets")
public class TicketEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "BIGINT COMMENT 'Match id this ticket batch belongs to'")
  private Long matchId;

  @Column(nullable = false, columnDefinition = "VARCHAR(50) COMMENT 'Seat category'")
  private String seatCategory;

  @Column(nullable = false, columnDefinition = "DECIMAL(10,2) COMMENT 'Ticket price'")
  private BigDecimal price;

  @Column(columnDefinition = "VARCHAR(500) COMMENT 'Ticket description'")
  private String description;

  @Column(nullable = false, columnDefinition = "INT COMMENT 'Total number of tickets in this batch'")
  private Integer totalQuantity;

  @Column(nullable = false, columnDefinition = "INT DEFAULT 0 COMMENT 'Number of tickets sold so far'")
  private Integer soldQuantity;

  @Column(columnDefinition = "DATETIME COMMENT 'Sale start time'")
  private LocalDateTime saleStartAt;

  @Column(columnDefinition = "DATETIME COMMENT 'Sale end time'")
  private LocalDateTime saleEndAt;

  @Column(nullable = false, columnDefinition = "INT DEFAULT 0 COMMENT 'Ticket status: 0=AVAILABLE, 1=SOLD_OUT, 2=CLOSED, 3=CANCELLED'")
  private int status;

  @Column(nullable = false, columnDefinition = "DATETIME COMMENT 'Created at'")
  private LocalDateTime createdAt;

  @Column(nullable = false, columnDefinition = "DATETIME COMMENT 'Updated at'")
  private LocalDateTime updatedAt;
}
