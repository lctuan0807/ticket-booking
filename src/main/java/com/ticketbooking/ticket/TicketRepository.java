package com.ticketbooking.ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

  @Query("SELECT t FROM TicketEntity t WHERE t.status = :status AND t.soldQuantity < t.totalQuantity "
      + "AND (:matchId IS NULL OR t.matchId = :matchId)")
  List<TicketEntity> findAvailableTickets(@Param("status") int status, @Param("matchId") Long matchId);

  @Query("SELECT t FROM TicketEntity t WHERE t.matchId = :matchId AND (:status IS NULL OR t.status = :status)")
  List<TicketEntity> findByMatchId(@Param("matchId") Long matchId, @Param("status") Integer status);
}
