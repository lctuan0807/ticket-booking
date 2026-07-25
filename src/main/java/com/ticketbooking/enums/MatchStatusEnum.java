package com.ticketbooking.enums;

public enum MatchStatusEnum {
  SCHEDULED, ONGOING, FINISHED, POSTPONED, CANCELLED;

  public static MatchStatusEnum fromInt(int value) {
    return MatchStatusEnum.values()[value];
  }

  public int toInt() {
    return this.ordinal();
  }
}
