package com.example.inventory_pro.venda.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VendaItemCreateDTO {

  @NotNull
  private Long produtoId;

  @Min(1)
  @NotNull
  private Integer quantidade;
}
