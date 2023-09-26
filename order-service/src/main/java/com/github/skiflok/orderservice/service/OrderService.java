package com.github.skiflok.orderservice.service;

import com.github.skiflok.orderservice.dto.OrderLinesItemsDto;
import com.github.skiflok.orderservice.dto.OrderRequest;
import com.github.skiflok.orderservice.model.Order;
import com.github.skiflok.orderservice.model.OrderLineItems;
import com.github.skiflok.orderservice.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  public void placeOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());

    final List<OrderLineItems> orderLineItems = orderRequest.getOrderLinesItemsDtoList()
        .stream()
        .map(this::mapToDto)
        .toList();

    order.setOrderLineItemsList(orderLineItems);

    orderRepository.save(order);
  }

  private OrderLineItems mapToDto(OrderLinesItemsDto orderLinesItemsDto) {
    OrderLineItems orderLineItems = new OrderLineItems();
    orderLineItems.setPrice(orderLinesItemsDto.getPrice());
    orderLineItems.setQuantity(orderLinesItemsDto.getQuantity());
    orderLineItems.setScuCode(orderLinesItemsDto.getScuCode());
    return orderLineItems;
  }
}
