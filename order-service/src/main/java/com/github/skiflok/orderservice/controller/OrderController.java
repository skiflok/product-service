package com.github.skiflok.orderservice.controller;

import com.github.skiflok.orderservice.dto.OrderRequest;
import com.github.skiflok.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order/")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @CircuitBreaker(name = "inventory", fallbackMethod = "failbackMethod")
  public String placeOrder(@RequestBody OrderRequest orderRequest){
    try {
      log.info("orderRequest.toString()");
      log.info(orderRequest.toString());
      orderService.placeOrder(orderRequest);
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    }
    return "Order Placed Successfully";
  }

  public String failbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
    return "Oops! Something wrong, please order after some time";
  }

}
