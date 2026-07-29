package com.ticketbooking.common;

public enum ResultCode {

  SUCCESS(200, "Successful"),
  INTERNAL_ERROR(500, "Internal error");

  private final Integer code;
  private final String message;

  ResultCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer code() {
    return this.code;
  }

  public String message() {
    return this.message;
  }
}
