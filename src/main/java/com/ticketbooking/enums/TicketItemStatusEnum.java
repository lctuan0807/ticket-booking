package com.ticketbooking.enums;

public enum TicketItemStatusEnum {
  ISSUED, RESERVED, SOLD, CANCELLED;

  public static TicketItemStatusEnum fromInt(int value) {
    return TicketItemStatusEnum.values()[value];
  }

  public int toInt() {
    return this.ordinal();
  }
}
