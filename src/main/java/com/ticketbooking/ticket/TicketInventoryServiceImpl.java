package com.ticketbooking.ticket;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketInventoryServiceImpl implements TicketInventoryService {

  private final TicketRepository ticketRepository;

  // Single transaction/connection for the whole reservation: a plain findById
  // fails fast on the common "already sold out" case without taking a row
  // lock, and the guarded UPDATE is the sole source of truth for correctness
  // under concurrency (its WHERE clause is the actual overselling guard).
  //
  // `ticket` stays untouched after loading: it's a JPA-managed entity, and
  // mutating its fields would mark it dirty, causing Hibernate to auto-flush
  // an unguarded full-row UPDATE at commit that clobbers the guarded update
  // above with this request's own stale values. The response is built on a
  // plain (non-managed) DTO instead.
  @Override
  @Transactional
  public TicketDTO reserveTickets(Long ticketId, int quantity) {
    TicketEntity ticket = ticketRepository.findById(ticketId)
        .orElseThrow(() -> new TicketNotFoundException(ticketId));

    int remaining = ticket.getTotalQuantity() - ticket.getSoldQuantity();
    if (quantity > remaining) {
      throw new TicketNotAvailableException(
          "Ticket batch " + ticketId + " has only " + remaining + " ticket(s) left, requested " + quantity);
    }

    int updated = ticketRepository.updateSoldQuantity(ticketId, quantity, TicketStatusEnum.SOLD_OUT.toInt());
    if (updated == 0) {
      throw new TicketNotAvailableException(
          "Ticket batch " + ticketId + " is no longer available, please try again");
    }

    int newSoldQuantity = ticket.getSoldQuantity() + quantity;

    log.info("Reserved {} ticket(s) from batch id={}, soldQuantity={}, remaining={}", quantity, ticketId,
        newSoldQuantity, remaining);
    return TicketMapper.toDTO(ticket);
  }
}
