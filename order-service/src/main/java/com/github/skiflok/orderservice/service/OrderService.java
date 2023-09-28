package com.github.skiflok.orderservice.service;

import com.github.skiflok.orderservice.dto.InventoryResponse;
import com.github.skiflok.orderservice.dto.OrderLinesItemsDto;
import com.github.skiflok.orderservice.dto.OrderRequest;
import com.github.skiflok.orderservice.model.Order;
import com.github.skiflok.orderservice.model.OrderLineItems;
import com.github.skiflok.orderservice.repository.OrderRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

  private final OrderRepository orderRepository;
  private final WebClient webClient;

  public void placeOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());

    final List<OrderLineItems> orderLineItems = orderRequest.getOrderLinesItemsDtoList()
        .stream()
        .map(this::mapToDto)
        .toList();

    order.setOrderLineItemsList(orderLineItems);

    List<String> scuCodes = order.getOrderLineItemsList().stream()
        .map(OrderLineItems::getScuCode)
        .toList();

    // Call inventory service, and place order if product is in
    // stock
    InventoryResponse[] inventoryResponses = webClient.get()
        .uri("http://localhost:8082/api/inventory",
            uriBuilder -> uriBuilder.queryParam("scuCode", scuCodes).build())
        .retrieve()
        .bodyToMono(InventoryResponse[].class)
        .block();

    boolean allProductInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

    if (Boolean.TRUE.equals(allProductInStock)) {
      orderRepository.save(order);
    } else {
      throw new IllegalArgumentException("Product is not in stock, please try again later");
    }

  }

  private OrderLineItems mapToDto(OrderLinesItemsDto orderLinesItemsDto) {
    OrderLineItems orderLineItems = new OrderLineItems();
    orderLineItems.setPrice(orderLinesItemsDto.getPrice());
    orderLineItems.setQuantity(orderLinesItemsDto.getQuantity());
    orderLineItems.setScuCode(orderLinesItemsDto.getScuCode());
    return orderLineItems;
  }
}
