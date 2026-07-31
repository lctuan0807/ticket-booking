package com.ticketbooking.order;

public enum OrderStatusEnum {
  CONFIRMED, CANCELLED, FAILED;

  public static OrderStatusEnum fromInt(int value) {
    return OrderStatusEnum.values()[value];
  }

  public int toInt() {
    return this.ordinal();
  }
}
