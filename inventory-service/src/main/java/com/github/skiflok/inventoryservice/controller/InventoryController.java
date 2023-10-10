package com.github.skiflok.inventoryservice.controller;

import com.github.skiflok.inventoryservice.dto.InventoryResponse;
import com.github.skiflok.inventoryservice.service.InventoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Log4j2
public class InventoryController {

  private final InventoryService inventoryService;

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  public List<InventoryResponse> isInStock(@RequestParam List<String> scuCode) {
    log.info("RequestParam scuCode = {}", scuCode);
    System.out.println(scuCode);
    List<InventoryResponse> inventoryResponses = inventoryService.isInStock(scuCode);
    log.info("inventoryResponses = {}", inventoryResponses);
    return inventoryResponses;
  }

}
