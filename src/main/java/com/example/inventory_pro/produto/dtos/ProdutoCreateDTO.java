package com.example.inventory_pro.produto.dtos;

import com.example.inventory_pro.produto.Produto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCreateDTO {
  @NotNull
  String nome;

  @NotNull
  String descricao;

  @NotNull
  int valor;

  @NotNull
  int quantidade;

  public ProdutoCreateDTO(Produto produto) {
    this.nome = produto.getNome();
    this.descricao = produto.getDescricao();
    this.valor = produto.getValor();
    this.quantidade = produto.getQuantidade();
  }
}
