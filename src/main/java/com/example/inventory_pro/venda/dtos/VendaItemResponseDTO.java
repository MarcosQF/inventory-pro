package com.example.inventory_pro.venda.dtos;

import java.text.NumberFormat;
import java.util.Locale;

import com.example.inventory_pro.venda.VendaItem;

import lombok.Data;

@Data
public class VendaItemResponseDTO {

  private static final Locale LOCALE_BR = Locale.forLanguageTag("pt-BR");

  private Long itemId;
  private Long produtoId;
  private String produtoNome;
  private Integer quantidade;

  private String valorUnitario;
  private String valorTotal;

  private String formatarMoeda(int valorEmCentavos) {
    double valor = valorEmCentavos / 100.0;
    NumberFormat nf = NumberFormat.getCurrencyInstance(LOCALE_BR);
    return nf.format(valor);
  }

  public VendaItemResponseDTO(VendaItem item) {
    this.itemId = item.getId();
    this.produtoId = item.getProduto().getId();
    this.produtoNome = item.getProduto().getNome();
    this.quantidade = item.getQuantidade();
    this.valorUnitario = formatarMoeda(item.getValorUnitario());
    this.valorTotal = formatarMoeda(item.getValorTotal());
  }
}
