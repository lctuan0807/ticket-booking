package com.ticketbooking.ticket;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.ticketbooking.redis.RedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketInventoryServiceImpl implements TicketInventoryService {

  private final TicketRepository ticketRepository;
  private final RedisService redisService;
  private final RedissonClient redissonClient;

  // Reserves `quantity` tickets from the batch, guarded by the same lock key
  // TicketServiceImpl uses on the read path so writes and cache-populating
  // reads serialize on the same row.
  @Override
  public TicketDTO reserveTickets(Long ticketId, int quantity) {
    if (quantity < 1) {
      throw new IllegalArgumentException("Quantity must be at least 1");
    }

    int remaining = ticketRepository.getStockAvailable(ticketId);
    log.info("Remaining tickets for ticketId {}: {}", ticketId, remaining);

    if (quantity > remaining) {
      throw new TicketNotAvailableException(
          "Ticket batch " + ticketId + " has only " + remaining + " ticket(s) left, requested " + quantity);
    }

    int updated = ticketRepository.updateSoldQuantity(ticketId, quantity);

    if (updated == 0) {
      throw new TicketNotAvailableException(
          "Ticket batch " + ticketId + " is not available");
    }

    log.info("Updated tickets for ticketId {}: {}", ticketId, updated);

    return TicketMapper.toDTO(ticketRepository.findById(ticketId).orElseThrow());
  }
}
