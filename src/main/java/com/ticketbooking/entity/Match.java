package com.ticketbooking.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "matches")
public class Match {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "VARCHAR(150) COMMENT 'Match competition name'")
  private String competition;

  @Column(nullable = false, columnDefinition = "VARCHAR(50) COMMENT 'Match stage'")
  private String stage;

  @Column(nullable = false, columnDefinition = "VARCHAR(20) COMMENT 'Match season'")
  private String season;

  @Column(nullable = false, columnDefinition = "VARCHAR(100) COMMENT 'Home team name'")
  private String homeTeam;

  @Column(nullable = false, columnDefinition = "VARCHAR(100) COMMENT 'Away team name'")
  private String awayTeam;

  @Column(nullable = false, columnDefinition = "DATETIME COMMENT 'Match date'")
  private LocalDateTime matchDate;

  @Column(nullable = false, columnDefinition = "VARCHAR(100) COMMENT 'Stadium name'")
  private String stadiumName;

  @Column(nullable = false, columnDefinition = "INT DEFAULT 0 COMMENT 'Match status: 0=SCHEDULED, 1=ONGOING, 2=FINISHED, 3=POSTPONED, 4=CANCELLED'")
  private int status; // SCHEDULED, ONGOING, FINISHED, POSTPONED, CANCELLED

  @Column(nullable = false, columnDefinition = "DATETIME COMMENT 'Created at'")
  private LocalDateTime createdAt;

  @Column(nullable = false, columnDefinition = "DATETIME COMMENT 'Updated at'")
  private LocalDateTime updatedAt;
}
