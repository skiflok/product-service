package com.github.skiflok.orderservice.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLinesItemsDto {
  private Long id;
  private String scuCode;
  private BigDecimal price;
  private Integer quantity;
}
