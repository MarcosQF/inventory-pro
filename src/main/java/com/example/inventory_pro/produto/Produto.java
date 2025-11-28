package com.example.inventory_pro.produto;

import com.example.inventory_pro.exceptions.ConflictException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
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
  @Min(1)
  int valor;

  @Column(nullable = false)
  @Min(1)
  Integer quantidade;

  public void update(String nome, String descricao, Integer valor, Integer quantidade) {
    if (nome != null)
      this.nome = nome;
    if (descricao != null)
      this.descricao = descricao;
    if (valor != null)
      this.valor = valor;
    if (quantidade != null)
      this.quantidade = quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    if (quantidade == null || quantidade < 0) {
      throw new ConflictException("Quantidade não pode ser negativa");
    }
    this.quantidade = quantidade;
  }
}
