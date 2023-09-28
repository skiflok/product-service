package com.github.skiflok.inventoryservice.service;

import com.github.skiflok.inventoryservice.dto.InventoryResponse;
import com.github.skiflok.inventoryservice.repository.InventoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

  @Autowired
  private final InventoryRepository inventoryRepository;

  @Transactional(readOnly = true)
  public List<InventoryResponse> isInStock(List<String> skuCode){
      return inventoryRepository.findByScuCodeIn(skuCode).stream()
          .map(inventory ->
            InventoryResponse.builder()
                .csuCode(inventory.getScuCode())
                .isInStock(inventory.getQuantity() > 0)
                .build()
          ).toList();
  }

}
