package com.github.skiflok.orderservice.service;

import com.github.skiflok.orderservice.dto.InventoryResponse;
import com.github.skiflok.orderservice.dto.OrderLinesItemsDto;
import com.github.skiflok.orderservice.dto.OrderRequest;
import com.github.skiflok.orderservice.model.Order;
import com.github.skiflok.orderservice.model.OrderLineItems;
import com.github.skiflok.orderservice.repository.OrderRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderService {

  private final OrderRepository orderRepository;
  private final WebClient.Builder webClientBuilder;

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

    log.info("scuCodes = {}",scuCodes);

//    System.out.println("\n" + scuCodes + "\n");

    // Call inventory service, and place order if product is in
    // stock
    InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
        .uri("http://inventory-service/api/inventory",
            uriBuilder ->
                uriBuilder.queryParam("scuCode", scuCodes).build())
        .retrieve()
        .bodyToMono(InventoryResponse[].class)
        .block();

    System.out.println("\n" + Arrays.toString(inventoryResponses) + "\n");

    log.info("inventoryResponses = {}", (Object) inventoryResponses);

    boolean allProductInStock = inventoryResponses != null && inventoryResponses.length != 0 && Arrays.stream(inventoryResponses)
        .allMatch(InventoryResponse::isInStock);

    log.info("boolean allProductInStock = {}", allProductInStock);

    if (Boolean.TRUE.equals(allProductInStock)) {
      orderRepository.save(order);
      log.info("orderRepository.save(order)");
    } else {
      log.info("IllegalArgumentException");
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
