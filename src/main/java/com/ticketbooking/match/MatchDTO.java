package com.ticketbooking.match;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchDTO {
  private Long id;
  private String competition;
  private String stage;
  private String season;
  private String homeTeam;
  private String awayTeam;
  private LocalDateTime matchDate;
  private String stadiumName;
}
