package com.example.inventory_pro.produto.dtos;

import com.example.inventory_pro.produto.Produto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoUpdateDTO {

  private String nome;
  private String descricao;
  private Integer valor;

  @Min(value = 0)
  private Integer quantidade;

  public ProdutoUpdateDTO(Produto produto) {
    this.nome = produto.getNome();
    this.descricao = produto.getDescricao();
    this.valor = produto.getValor();
    this.quantidade = produto.getQuantidade();
  }
}
