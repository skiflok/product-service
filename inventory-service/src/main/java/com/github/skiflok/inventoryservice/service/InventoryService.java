package com.github.skiflok.inventoryservice.service;

import com.github.skiflok.inventoryservice.dto.InventoryResponse;
import com.github.skiflok.inventoryservice.repository.InventoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

  @Autowired
  private final InventoryRepository inventoryRepository;

  @Transactional(readOnly = true)
  @SneakyThrows
  public List<InventoryResponse> isInStock(List<String> skuCode){
    log.info("Wait started");
//    Thread.sleep(10000);
    log.info("Wait ended");
      return inventoryRepository.findByScuCodeIn(skuCode).stream()
          .map(inventory ->
            InventoryResponse.builder()
                .scuCode(inventory.getScuCode())
                .isInStock(inventory.getQuantity() > 0)
                .build()
          ).toList();
  }

}
