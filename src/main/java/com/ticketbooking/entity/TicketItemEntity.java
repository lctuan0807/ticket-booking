package com.ticketbooking.entity;

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
@Table(name = "ticket_items")
public class TicketItemEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "BIGINT COMMENT 'Ticket batch id this item belongs to'")
  private Long ticketId;

  @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(50) COMMENT 'Unique ticket code for this seat instance'")
  private String ticketCode;

  @Column(nullable = false, columnDefinition = "VARCHAR(50) COMMENT 'Seat section'")
  private String seatSection;

  @Column(nullable = false, columnDefinition = "VARCHAR(10) COMMENT 'Seat row'")
  private String seatRow;

  @Column(nullable = false, columnDefinition = "VARCHAR(10) COMMENT 'Seat number'")
  private String seatNumber;

  @Column(nullable = false, columnDefinition = "INT DEFAULT 0 COMMENT 'Ticket item status: 0=ISSUED, 1=RESERVED, 2=SOLD, 3=CANCELLED'")
  private int status;

  @Column(nullable = false, columnDefinition = "DATETIME COMMENT 'Created at'")
  private LocalDateTime createdAt;

  @Column(nullable = false, columnDefinition = "DATETIME COMMENT 'Updated at'")
  private LocalDateTime updatedAt;
}
