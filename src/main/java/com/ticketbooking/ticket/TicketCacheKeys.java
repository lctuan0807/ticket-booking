package com.ticketbooking.ticket;

/**
 * Shared cache/lock key format for a ticket batch, used by both TicketServiceImpl
 * (reads) and TicketInventoryServiceImpl (reservation) so they stay in sync on the
 * same Redis key.
 */
public class TicketCacheKeys {
  public static String genTicketKey(Long ticketId) {
    return "MATCH:TICKET:" + ticketId;
  }

  public static String genTicketLockKey(Long ticketId) {
    return "TICKET:LOCK:" + ticketId;
  }
}
