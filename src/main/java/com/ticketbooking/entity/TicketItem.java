package com.ticketbooking.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ticket_items")
public class TicketItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "seat_category", nullable = false)
  private String seatCategory;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "buyer_name", nullable = false)
  private String buyerName;

  @Column(name = "buyer_email", nullable = false)
  private String buyerEmail;

  @Column(name = "status", nullable = false)
  private int status; // 0=INACTIVE, 1=ACTIVE

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ticket_id", nullable = false)
  private Ticket ticket;
}
