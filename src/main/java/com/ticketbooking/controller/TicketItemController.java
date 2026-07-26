package com.ticketbooking.controller;

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

import com.ticketbooking.dto.CreateTicketItemRequest;
import com.ticketbooking.dto.TicketItemDTO;
import com.ticketbooking.exception.TicketItemNotFoundException;
import com.ticketbooking.service.TicketItemService;
import com.ticketbooking.util.ResultMessage;
import com.ticketbooking.util.ResultUtil;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/ticket-items")
@Slf4j
@RequiredArgsConstructor
public class TicketItemController {

  private final TicketItemService ticketItemService;

  @PostMapping
  public ResponseEntity<ResultMessage<TicketItemDTO>> createTicketItem(
      @RequestBody @Valid CreateTicketItemRequest request) {
    try {
      return ResponseEntity.ok(ResultUtil.data(ticketItemService.createTicketItem(request)));
    } catch (Exception e) {
      log.error("Error creating ticket item", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to create ticket item"));
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResultMessage<TicketItemDTO>> getTicketItem(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(ResultUtil.data(ticketItemService.getTicketItem(id)));
    } catch (TicketItemNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultUtil.error(404, e.getMessage()));
    } catch (Exception e) {
      log.error("Error getting ticket item", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to get ticket item"));
    }
  }

  @GetMapping
  public ResponseEntity<ResultMessage<List<TicketItemDTO>>> listTicketItems() {
    try {
      return ResponseEntity.ok(ResultUtil.data(ticketItemService.listTicketItems()));
    } catch (Exception e) {
      log.error("Error listing ticket items", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to list ticket items"));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResultMessage<TicketItemDTO>> updateTicketItem(@PathVariable Long id,
      @RequestBody @Valid CreateTicketItemRequest request) {
    try {
      return ResponseEntity.ok(ResultUtil.data(ticketItemService.updateTicketItem(id, request)));
    } catch (TicketItemNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultUtil.error(404, e.getMessage()));
    } catch (Exception e) {
      log.error("Error updating ticket item", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to update ticket item"));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResultMessage<Void>> deleteTicketItem(@PathVariable Long id) {
    try {
      ticketItemService.deleteTicketItem(id);
      return ResponseEntity.ok(ResultUtil.success());
    } catch (TicketItemNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultUtil.error(404, e.getMessage()));
    } catch (Exception e) {
      log.error("Error deleting ticket item", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to delete ticket item"));
    }
  }
}
