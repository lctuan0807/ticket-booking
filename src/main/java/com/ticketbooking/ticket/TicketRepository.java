package com.ticketbooking.ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

  @Query("SELECT t FROM TicketEntity t WHERE t.status = :status AND t.soldQuantity < t.totalQuantity "
      + "AND (:matchId IS NULL OR t.matchId = :matchId)")
  List<TicketEntity> findAvailableTickets(@Param("status") int status, @Param("matchId") Long matchId);

  @Query("SELECT t FROM TicketEntity t WHERE t.matchId = :matchId AND (:status IS NULL OR t.status = :status)")
  List<TicketEntity> findByMatchId(@Param("matchId") Long matchId, @Param("status") Integer status);

  @Query("SELECT t.totalQuantity - t.soldQuantity FROM TicketEntity t WHERE t.id = :ticketId")
  int getStockAvailable(@Param("ticketId") Long ticketId);

  @Modifying
  @Transactional
  // @Query("UPDATE TicketEntity t SET t.soldQuantity = :soldQuantity WHERE t.id =
  // :ticketId")
  @Query("UPDATE TicketEntity t SET t.updatedAt = CURRENT_TIMESTAMP, t.soldQuantity = t.soldQuantity + :soldQuantity, "
      + "t.status = CASE WHEN t.soldQuantity + :soldQuantity = t.totalQuantity THEN :soldOutStatus ELSE t.status END "
      + "WHERE t.id = :ticketId AND t.soldQuantity + :soldQuantity <= t.totalQuantity")
  int updateSoldQuantity(@Param("ticketId") Long ticketId, @Param("soldQuantity") int soldQuantity,
      @Param("soldOutStatus") int soldOutStatus);
}
