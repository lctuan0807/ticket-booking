package com.ticketbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketbooking.entity.TicketItemEntity;

public interface TicketItemRepository extends JpaRepository<TicketItemEntity, Long> {
}
