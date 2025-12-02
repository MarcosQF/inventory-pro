package com.example.inventorypro.venda.dtos;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VendaCreateDTO {

  @NotEmpty(message = "A venda deve conter ao menos 1 item")
  private List<VendaItemCreateDTO> itens;
}
