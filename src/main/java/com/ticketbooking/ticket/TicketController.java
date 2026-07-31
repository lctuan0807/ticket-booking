package com.ticketbooking.ticket;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticketbooking.common.ResultMessage;
import com.ticketbooking.common.ResultUtil;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/tickets")
@Slf4j
@RequiredArgsConstructor
public class TicketController {

  private final TicketService ticketService;

  /**
   * Purpose: Create a new ticket batch.
   * Endpoint: POST /api/v1/tickets
   * Params: request (body) - CreateTicketRequest with ticket batch details.
   * Return: 200 with the created TicketDTO; 500 on failure.
   */
  @PostMapping
  public ResponseEntity<ResultMessage<TicketDTO>> createTicket(@RequestBody @Valid CreateTicketRequest request) {
    try {
      return ResponseEntity.ok(ResultUtil.data(ticketService.createTicket(request)));
    } catch (Exception e) {
      log.error("Error creating ticket", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to create ticket"));
    }
  }

  /**
   * Purpose: Fetch ticket batches that are still purchasable (status AVAILABLE and
   * soldQuantity less than totalQuantity), optionally scoped to a single match.
   * Endpoint: GET /api/v1/tickets/available
   * Params: matchId (query, optional) - id of the match to filter by; omit to return
   * available tickets across all matches.
   * Return: 200 with the list of available TicketDTO; 500 on failure.
   */
  @GetMapping("/available")
  public ResponseEntity<ResultMessage<List<TicketDTO>>> getAvailableTickets(
      @RequestParam(required = false) Long matchId) {
    try {
      return ResponseEntity.ok(ResultUtil.data(ticketService.getAvailableTickets(matchId)));
    } catch (Exception e) {
      log.error("Error getting available tickets", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to get available tickets"));
    }
  }

  /**
   * Purpose: Fetch a single ticket batch by id, via cache-aside with a distributed lock
   * on cache miss to avoid a stampede of concurrent DB reads.
   * Endpoint: GET /api/v1/tickets/{id}
   * Params: id (path) - id of the ticket to fetch.
   * Return: 200 with the TicketDTO; 404 if not found; 500 on failure.
   */
  @GetMapping("/{id}")
  public ResponseEntity<ResultMessage<TicketDTO>> getTicket(@PathVariable Long id) {
    log.info("Controller | Getting ticket with id: {}", id);
    try {
      // return
      // ResponseEntity.ok(ResultUtil.data(ticketService.getTicketWithCache(id)));
      return ResponseEntity.ok(ResultUtil.data(ticketService.getTicketWithDistributedLockCache(id)));
    } catch (TicketNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultUtil.error(404, e.getMessage()));
    } catch (Exception e) {
      log.error("Error getting ticket", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to get ticket"));
    }
  }

  /**
   * Purpose: List every ticket batch, regardless of status.
   * Endpoint: GET /api/v1/tickets
   * Params: none.
   * Return: 200 with the list of all TicketDTO; 500 on failure.
   */
  @GetMapping
  public ResponseEntity<ResultMessage<List<TicketDTO>>> listTickets() {
    try {
      return ResponseEntity.ok(ResultUtil.data(ticketService.listTickets()));
    } catch (Exception e) {
      log.error("Error listing tickets", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to list tickets"));
    }
  }

  /**
   * Purpose: Update an existing ticket batch.
   * Endpoint: PUT /api/v1/tickets/{id}
   * Params: id (path) - id of the ticket to update; request (body) - CreateTicketRequest
   * with the new ticket batch details.
   * Return: 200 with the updated TicketDTO; 404 if not found; 500 on failure.
   */
  @PutMapping("/{id}")
  public ResponseEntity<ResultMessage<TicketDTO>> updateTicket(@PathVariable Long id,
      @RequestBody @Valid CreateTicketRequest request) {
    try {
      return ResponseEntity.ok(ResultUtil.data(ticketService.updateTicket(id, request)));
    } catch (TicketNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultUtil.error(404, e.getMessage()));
    } catch (Exception e) {
      log.error("Error updating ticket", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to update ticket"));
    }
  }

  /**
   * Purpose: Delete a ticket batch by id.
   * Endpoint: DELETE /api/v1/tickets/{id}
   * Params: id (path) - id of the ticket to delete.
   * Return: 200 on success; 404 if not found; 500 on failure.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ResultMessage<Void>> deleteTicket(@PathVariable Long id) {
    try {
      ticketService.deleteTicket(id);
      return ResponseEntity.ok(ResultUtil.success());
    } catch (TicketNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultUtil.error(404, e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to delete ticket"));
    }
  }
}
