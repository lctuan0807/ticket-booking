package com.ticketbooking.order;

public interface OrderService {
  OrderDTO placeOrder(CreateOrderRequest request);
}
