package com.example.inventory_pro.produto.dtos;

import com.example.inventory_pro.produto.Produto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoResponseDTO {
  Long id;
  String nome;
  String descricao;
  String valor;
  int quantidade;

  private static final Locale LOCALE_BR = Locale.forLanguageTag("pt-BR");

  public ProdutoResponseDTO(Produto produto) {
    this.id = produto.getId();
    this.nome = produto.getNome();
    this.descricao = produto.getDescricao();
    this.quantidade = produto.getQuantidade();

    this.valor = formatarMoeda(produto.getValor());
  }

  private String formatarMoeda(int valorEmCentavos) {
    double valor = valorEmCentavos / 100.0;
    NumberFormat nf = NumberFormat.getCurrencyInstance(LOCALE_BR);
    return nf.format(valor);
  }
}
