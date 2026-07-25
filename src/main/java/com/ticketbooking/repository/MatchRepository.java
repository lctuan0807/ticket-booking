package com.ticketbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketbooking.entity.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
