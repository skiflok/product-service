package com.github.skiflok.orderservice.repository;

import com.github.skiflok.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
