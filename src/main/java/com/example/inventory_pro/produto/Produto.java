package com.example.inventory_pro.produto;

import com.example.inventory_pro.produto.dtos.ProdutoCreateDTO;
import com.example.inventory_pro.produto.dtos.ProdutoResponseDTO;
import com.example.inventory_pro.produto.dtos.ProdutoUpdateDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Produto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @NotBlank(message = "Nome é obrigatório")
  @Column(nullable = false)
  String nome;

  @NotBlank(message = "Descrição é obrigatório")
  @Column(nullable = false)
  String descricao;

  @Column(nullable = false)
  int valor;

  @Column(nullable = false)
  int quantidade;

  public Produto(ProdutoCreateDTO produtoCreateDTO) {
    this.nome = produtoCreateDTO.getNome();
    this.descricao = produtoCreateDTO.getDescricao();
    this.valor = produtoCreateDTO.getValor();
    this.quantidade = produtoCreateDTO.getQuantidade();
  }

  public Produto(ProdutoResponseDTO produtoResponseDTO) {
    this.id = produtoResponseDTO.getId();
    this.nome = produtoResponseDTO.getNome();
    this.descricao = produtoResponseDTO.getDescricao();
    this.valor = produtoResponseDTO.getValor();
    this.quantidade = produtoResponseDTO.getQuantidade();
  }

  public Produto(ProdutoUpdateDTO produtoUpdateDTO) {
    this.nome = produtoUpdateDTO.getNome();
    this.descricao = produtoUpdateDTO.getDescricao();
    this.valor = produtoUpdateDTO.getValor();
    this.quantidade = produtoUpdateDTO.getQuantidade();
  }
}
