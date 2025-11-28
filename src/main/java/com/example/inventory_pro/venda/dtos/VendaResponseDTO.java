package com.example.inventory_pro.venda.dtos;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import com.example.inventory_pro.venda.Venda;
import lombok.Data;

@Data
public class VendaResponseDTO {
  private static final Locale LOCALE_BR = Locale.forLanguageTag("pt-BR");

  private Long id;
  private LocalDateTime dataVenda;
  private String valorTotal;
  private List<VendaItemResponseDTO> itens;

  private String formatarMoeda(int valorEmCentavos) {
    double valor = valorEmCentavos / 100.0;
    NumberFormat nf = NumberFormat.getCurrencyInstance(LOCALE_BR);
    return nf.format(valor);
  }

  public VendaResponseDTO(Venda venda) {
    this.id = venda.getId();
    this.dataVenda = venda.getDataVenda();
    this.valorTotal = formatarMoeda(venda.getValorTotal());

    this.itens = venda.getItens().stream()
        .map(VendaItemResponseDTO::new)
        .toList();
  }
}
