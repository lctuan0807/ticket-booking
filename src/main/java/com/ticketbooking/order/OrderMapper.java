package com.ticketbooking.order;

public class OrderMapper {
  public static OrderDTO toDTO(OrderEntity entity) {
    OrderDTO dto = new OrderDTO();
    dto.setId(entity.getId());
    dto.setUserId(entity.getUserId());
    dto.setTicketId(entity.getTicketId());
    dto.setQuantity(entity.getQuantity());
    dto.setUnitPrice(entity.getUnitPrice());
    dto.setTotalAmount(entity.getTotalAmount());
    dto.setStatus(OrderStatusEnum.fromInt(entity.getStatus()));
    dto.setCreatedAt(entity.getCreatedAt());
    return dto;
  }
}
