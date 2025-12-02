package com.example.inventorypro.venda;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Venda {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<VendaItem> itens;

  @Column(nullable = false)
  private LocalDateTime dataVenda;

  @Column(nullable = false)
  private Integer valorTotal;

  public void calcularTotal() {
    this.valorTotal = itens.stream()
        .mapToInt(VendaItem::getValorTotal)
        .sum();
  }
}
