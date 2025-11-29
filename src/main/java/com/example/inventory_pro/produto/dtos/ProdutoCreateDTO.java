package com.example.inventory_pro.produto.dtos;

import com.example.inventory_pro.produto.Produto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoCreateDTO {

  @Valid

  @NotNull
  String nome;

  @NotNull
  String descricao;

  @NotNull
  int valor;

  @NotNull
  @Min(1)
  Integer quantidade;

  public ProdutoCreateDTO(Produto produto) {
    this.nome = produto.getNome();
    this.descricao = produto.getDescricao();
    this.valor = produto.getValor();
    this.quantidade = produto.getQuantidade();
  }
}
