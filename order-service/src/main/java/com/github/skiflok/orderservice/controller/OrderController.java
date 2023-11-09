package com.github.skiflok.orderservice.controller;

import com.github.skiflok.orderservice.dto.OrderRequest;
import com.github.skiflok.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import java.util.concurrent.CompletableFuture;
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
  @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
  @TimeLimiter(name = "inventory")
  @Retry(name = "inventory")
  public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
    try {
      log.info("orderRequest.toString()");
      log.info(orderRequest.toString());
      return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    } catch (IllegalArgumentException e) {
      return CompletableFuture.supplyAsync(e::getMessage);
    }
  }

  public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest,
      RuntimeException runtimeException) {
    return CompletableFuture.supplyAsync(
        () -> "Oops! Something wrong, please order after some time");
  }

}
