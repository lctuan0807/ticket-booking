package com.ticketbooking.dto;

import lombok.Data;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateMatchRequest {
  @NotBlank(message = "Competition cannot be empty")
  @Size(max = 150, message = "Competition cannot exceed 150 characters")
  private String competition;

  @NotBlank(message = "Stage cannot be empty")
  @Size(max = 50, message = "Stage cannot exceed 50 characters")
  private String stage;

  @NotBlank(message = "Season cannot be empty")
  @Size(max = 20, message = "Season cannot exceed 20 characters")
  private String season;

  @NotBlank(message = "Home team cannot be empty")
  @Size(max = 100, message = "Home team cannot exceed 100 characters")
  private String homeTeam;

  @NotBlank(message = "Away team cannot be empty")
  @Size(max = 100, message = "Away team cannot exceed 100 characters")
  private String awayTeam;

  @NotNull(message = "Match date is required")
  private LocalDateTime matchDate;

  @NotBlank(message = "Stadium name cannot be empty")
  @Size(max = 100, message = "Stadium name cannot exceed 100 characters")
  private String stadiumName;
}
