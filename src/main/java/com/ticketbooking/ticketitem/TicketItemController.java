package com.ticketbooking.ticketitem;

import java.util.List;

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

@RestController
@RequestMapping("/api/v1/ticket-items")
@RequiredArgsConstructor
public class TicketItemController {

  private final TicketItemService ticketItemService;

  /**
   * Purpose: Create a new ticket item.
   * Endpoint: POST /api/v1/ticket-items
   * Params: request (body) - CreateTicketItemRequest with ticket item details.
   * Return: 200 with the created TicketItemDTO; 500 on failure.
   */
  @PostMapping
  public ResponseEntity<ResultMessage<TicketItemDTO>> createTicketItem(
      @RequestBody @Valid CreateTicketItemRequest request) {
    return ResponseEntity.ok(ResultUtil.data(ticketItemService.createTicketItem(request)));
  }

  /**
   * Purpose: Fetch a single ticket item by id.
   * Endpoint: GET /api/v1/ticket-items/{id}
   * Params: id (path) - id of the ticket item to fetch.
   * Return: 200 with the TicketItemDTO; 404 if not found; 500 on failure.
   */
  @GetMapping("/{id}")
  public ResponseEntity<ResultMessage<TicketItemDTO>> getTicketItem(@PathVariable Long id) {
    return ResponseEntity.ok(ResultUtil.data(ticketItemService.getTicketItem(id)));
  }

  /**
   * Purpose: List every ticket item.
   * Endpoint: GET /api/v1/ticket-items
   * Params: none.
   * Return: 200 with the list of all TicketItemDTO; 500 on failure.
   */
  @GetMapping
  public ResponseEntity<ResultMessage<List<TicketItemDTO>>> listTicketItems() {
    return ResponseEntity.ok(ResultUtil.data(ticketItemService.listTicketItems()));
  }

  /**
   * Purpose: Update an existing ticket item.
   * Endpoint: PUT /api/v1/ticket-items/{id}
   * Params: id (path) - id of the ticket item to update; request (body) -
   * CreateTicketItemRequest with the new ticket item details.
   * Return: 200 with the updated TicketItemDTO; 404 if not found; 500 on failure.
   */
  @PutMapping("/{id}")
  public ResponseEntity<ResultMessage<TicketItemDTO>> updateTicketItem(@PathVariable Long id,
      @RequestBody @Valid CreateTicketItemRequest request) {
    return ResponseEntity.ok(ResultUtil.data(ticketItemService.updateTicketItem(id, request)));
  }

  /**
   * Purpose: Delete a ticket item by id.
   * Endpoint: DELETE /api/v1/ticket-items/{id}
   * Params: id (path) - id of the ticket item to delete.
   * Return: 200 on success; 404 if not found; 500 on failure.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ResultMessage<Void>> deleteTicketItem(@PathVariable Long id) {
    ticketItemService.deleteTicketItem(id);
    return ResponseEntity.ok(ResultUtil.success());
  }
}
