package com.ticketbooking.ticketitem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketItemRepository extends JpaRepository<TicketItemEntity, Long> {
}
