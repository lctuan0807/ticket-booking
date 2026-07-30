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

  @PostMapping
  public ResponseEntity<ResultMessage<TicketDTO>> createTicket(@RequestBody @Valid CreateTicketRequest request) {
    try {
      return ResponseEntity.ok(ResultUtil.data(ticketService.createTicket(request)));
    } catch (Exception e) {
      log.error("Error creating ticket", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to create ticket"));
    }
  }

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

  @GetMapping
  public ResponseEntity<ResultMessage<List<TicketDTO>>> listTickets() {
    try {
      return ResponseEntity.ok(ResultUtil.data(ticketService.listTickets()));
    } catch (Exception e) {
      log.error("Error listing tickets", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to list tickets"));
    }
  }

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

  @DeleteMapping("/{id}")
  public ResponseEntity<ResultMessage<Void>> deleteTicket(@PathVariable Long id) {
    try {
      ticketService.deleteTicket(id);
      return ResponseEntity.ok(ResultUtil.success());
    } catch (TicketNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultUtil.error(404, e.getMessage()));
    } catch (Exception e) {
      log.error("Error deleting ticket", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to delete ticket"));
    }
  }
}
