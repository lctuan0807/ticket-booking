package com.ticketbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketbooking.entity.TicketEntity;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
}
