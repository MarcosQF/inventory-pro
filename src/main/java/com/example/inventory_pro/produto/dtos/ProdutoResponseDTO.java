package com.example.inventory_pro.produto.dtos;

import com.example.inventory_pro.produto.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDTO {
  Long id;
  String nome;
  String descricao;
  int valor;
  int quantidade;

  public ProdutoResponseDTO(Produto produto) {
    this.id = produto.getId();
    this.nome = produto.getNome();
    this.descricao = produto.getDescricao();
    this.valor = produto.getValor();
    this.quantidade = produto.getQuantidade();
  }
}
