package com.ticketbooking.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateTicketRequest {
  @NotBlank(message = "Home team cannot be empty")
  @Size(max = 100, message = "Home team cannot exceed 100 characters")
  private String homeTeam;

  @NotBlank(message = "Away team cannot be empty")
  @Size(max = 100, message = "Away team cannot exceed 100 characters")
  private String awayTeam;

  @NotBlank(message = "Stage cannot be empty")
  @Size(max = 50, message = "Stage cannot exceed 50 characters")
  private String stage;

  @NotBlank(message = "Venue cannot be empty")
  @Size(max = 150, message = "Venue cannot exceed 150 characters")
  private String venue;

  @NotNull(message = "Match date is required")
  private LocalDateTime matchDate;

  @NotBlank(message = "Seat category cannot be empty")
  @Size(max = 50, message = "Seat category cannot exceed 50 characters")
  private String seatCategory;

  @NotNull(message = "Price is required")
  private BigDecimal price;

  @NotBlank(message = "Buyer name cannot be empty")
  @Size(max = 255, message = "Buyer name cannot exceed 255 characters")
  private String buyerName;

  @NotBlank(message = "Buyer email cannot be empty")
  @Email(message = "Buyer email must be a valid email address")
  @Size(max = 255, message = "Buyer email cannot exceed 255 characters")
  private String buyerEmail;
}
