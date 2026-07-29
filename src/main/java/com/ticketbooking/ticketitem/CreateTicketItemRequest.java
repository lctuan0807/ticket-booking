package com.ticketbooking.ticketitem;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CreateTicketItemRequest {
  @NotNull(message = "Ticket id is required")
  private Long ticketId;

  @NotBlank(message = "Ticket code cannot be empty")
  @Size(max = 50, message = "Ticket code cannot exceed 50 characters")
  private String ticketCode;

  @NotBlank(message = "Seat section cannot be empty")
  @Size(max = 50, message = "Seat section cannot exceed 50 characters")
  private String seatSection;

  @NotBlank(message = "Seat row cannot be empty")
  @Size(max = 10, message = "Seat row cannot exceed 10 characters")
  private String seatRow;

  @NotBlank(message = "Seat number cannot be empty")
  @Size(max = 10, message = "Seat number cannot exceed 10 characters")
  private String seatNumber;
}
