package com.github.skiflok.inventoryservice.service;

import com.github.skiflok.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

  @Autowired
  private final InventoryRepository inventoryRepository;

  public boolean isInStock(String skuCode){
      return inventoryRepository.findByScuCode(skuCode).isPresent();
  }

}
