package com.github.skiflok.inventoryservice.repository;

import com.github.skiflok.inventoryservice.model.Inventory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

  public Optional<Inventory> findByScuCode(String scuCode);

  List<Inventory> findByScuCodeIn(List<String> skuCode);
}
