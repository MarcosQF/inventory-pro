package com.example.inventorypro.venda;

import com.example.inventorypro.produto.Produto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "venda_id")
  private Venda venda;

  @ManyToOne(optional = false)
  @JoinColumn(name = "produto_id")
  private Produto produto;

  @Min(value = 1, message = "Quantidade mínima é 1")
  @Column(nullable = false)
  private Integer quantidade;

  @Column(nullable = false)
  private Integer valorUnitario;

  @Column(nullable = false)
  private Integer valorTotal;

  public void calcularTotal() {
    this.valorUnitario = produto.getValor();
    this.valorTotal = this.valorUnitario * this.quantidade;
  }
}
