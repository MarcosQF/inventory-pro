package com.example.inventorypro.produto.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.Locale;

import com.example.inventorypro.produto.Produto;

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
    double valorFormatado = valorEmCentavos / 100.0;
    NumberFormat nf = NumberFormat.getCurrencyInstance(LOCALE_BR);
    return nf.format(valorFormatado);
  }
}
