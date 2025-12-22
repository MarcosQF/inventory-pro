package com.example.inventorypro.produto.dtos;

import com.example.inventorypro.produto.Produto;

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

  @NotNull
  String nome;

  @NotNull
  String descricao;

  @Min(1)
  private Integer valor;

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
