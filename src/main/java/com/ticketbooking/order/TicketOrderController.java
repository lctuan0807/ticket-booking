package com.ticketbooking.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketbooking.common.ResultMessage;
import com.ticketbooking.common.ResultUtil;

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
   * Atomically checks and reserves availability (CAS-guarded update on the
   * ticket batch's sold quantity) before persisting the order.
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
    return ResponseEntity.ok(ResultUtil.data(orderService.placeOrder(request)));
  }
}
