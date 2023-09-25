package com.github.skiflok.productservice.dto;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ProductRequest(String name, String description, BigDecimal price) {
}
