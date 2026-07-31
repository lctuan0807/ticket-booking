package com.ticketbooking.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketbooking.common.ResultMessage;
import com.ticketbooking.common.ResultUtil;
import com.ticketbooking.ticket.TicketNotAvailableException;
import com.ticketbooking.ticket.TicketNotFoundException;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
@RequiredArgsConstructor
public class TicketOrderController {

  private final OrderService orderService;

  /**
   * Purpose: Place an order for a quantity of tickets from a ticket batch.
   * Atomically
   * checks and reserves availability (distributed lock guarded) before persisting
   * the order.
   * Endpoint: POST /api/v1/orders
   * Params: request (body) - CreateOrderRequest with userId, ticketId and
   * quantity.
   * Return: 200 with the created OrderDTO; 400 if the request is invalid; 404 if
   * the ticket
   * batch doesn't exist; 409 if the requested quantity isn't available; 500 on
   * failure.
   */
  @PostMapping
  public ResponseEntity<ResultMessage<OrderDTO>> placeOrder(@RequestBody @Valid CreateOrderRequest request) {
    log.info("Placing order: {}", request);
    try {
      return ResponseEntity.ok(ResultUtil.data(orderService.placeOrder(request)));
    } catch (TicketNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultUtil.error(404, e.getMessage()));
    } catch (TicketNotAvailableException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(ResultUtil.error(409, e.getMessage()));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultUtil.error(400, e.getMessage()));
    } catch (Exception e) {
      log.error("Error placing order", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to place order"));
    }
  }
}
