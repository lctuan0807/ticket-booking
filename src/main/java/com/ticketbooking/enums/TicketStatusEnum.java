package com.ticketbooking.enums;

public enum TicketStatusEnum {
  INACTIVE(0),
  ACTIVE(1),
  DELETED(2);

  private final int value;

  TicketStatusEnum(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
