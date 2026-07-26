package com.ticketbooking.enums;

public enum TicketStatusEnum {
  AVAILABLE, SOLD_OUT, CLOSED, CANCELLED;

  public static TicketStatusEnum fromInt(int value) {
    return TicketStatusEnum.values()[value];
  }

  public int toInt() {
    return this.ordinal();
  }
}
