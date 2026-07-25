package com.ticketbooking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "home_team", nullable = false)
  private String homeTeam;

  @Column(name = "away_team", nullable = false)
  private String awayTeam;

  @Column(name = "stage", nullable = false)
  private String stage;

  @Column(name = "stadium_name", nullable = false)
  private String stadiumName;

  @Column(name = "match_date", nullable = false)
  private LocalDateTime matchDate;

  @Column(name = "status", nullable = false)
  private int status; // 0=INACTIVE, 1=ACTIVE, 2=DELETED

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
