package com.github.skiflok.inventoryservice.controller;

import com.github.skiflok.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

  private final InventoryService inventoryService;

  @GetMapping("/{scu-code}")
  @ResponseStatus(HttpStatus.OK)
  public boolean isInStock(@PathVariable("sci-code") String scuCode) {
    return inventoryService.isInStock(scuCode);
  }

}
