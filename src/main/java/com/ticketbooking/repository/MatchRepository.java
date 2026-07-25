package com.ticketbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketbooking.entity.MatchEntity;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {
}
