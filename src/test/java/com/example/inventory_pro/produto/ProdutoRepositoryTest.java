package com.example.inventory_pro.produto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProdutoRepositoryTest {

  @Autowired
  private ProdutoRepository produtoRepository;

  Produto produto;

  @BeforeEach
  void setup() {
    produto = Produto.builder()
        .nome("Notebook Dell")
        .descricao("i5, 16GB RAM")
        .valor(350000)
        .quantidade(10)
        .build();
  }

  @Test
  void mustSaveProduto() {
    Produto saved = produtoRepository.save(produto);

    assertThat(saved.getId()).isNotNull();
    assertThat(saved.getNome()).isEqualTo("Notebook Dell");
  }

  @Test
  void mustFindProdutoById() {
    Produto saved = produtoRepository.save(produto);

    Produto found = produtoRepository.findById(saved.getId()).orElse(null);

    assertThat(found).isNotNull();
    assertThat(found.getNome()).isEqualTo("Notebook Dell");
  }

  @Test
  void mustListAllProdutos() {
    produtoRepository.save(produto);

    List<Produto> produtos = produtoRepository.findAll();

    assertThat(produtos).hasSize(1);
  }

  @Test
  void mustUpdateProduto() {
    Produto saved = produtoRepository.save(produto);

    saved.setQuantidade(20);
    Produto updated = produtoRepository.save(saved);

    assertThat(updated.getQuantidade()).isEqualTo(20);
  }

  @Test
  void mustDeleteProduto() {
    Produto saved = produtoRepository.save(produto);

    produtoRepository.deleteById(saved.getId());

    boolean exists = produtoRepository.findById(saved.getId()).isPresent();

    assertThat(exists).isFalse();
  }
}
