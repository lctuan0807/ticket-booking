package com.ticketbooking.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ticketbooking.ticket.TicketDTO;
import com.ticketbooking.ticket.TicketInventoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final TicketInventoryService ticketInventoryService;

  @Override
  public OrderDTO placeOrder(CreateOrderRequest request) {
    TicketDTO ticket = ticketInventoryService.reserveTickets(request.getTicketId(), request.getQuantity());

    OrderEntity entity = new OrderEntity();
    entity.setUserId(request.getUserId());
    entity.setTicketId(request.getTicketId());
    entity.setQuantity(request.getQuantity());
    entity.setUnitPrice(ticket.getPrice());
    entity.setTotalAmount(ticket.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
    entity.setStatus(OrderStatusEnum.CONFIRMED.toInt());
    entity.setCreatedAt(LocalDateTime.now());
    entity.setUpdatedAt(LocalDateTime.now());

    OrderEntity saved = orderRepository.save(entity);
    log.info("Placed order (id={}, ticketId={}, quantity={})", saved.getId(), saved.getTicketId(), saved.getQuantity());
    return OrderMapper.toDTO(saved);
  }
}
