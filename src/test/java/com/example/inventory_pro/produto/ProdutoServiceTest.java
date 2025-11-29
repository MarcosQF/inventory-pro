package com.example.inventory_pro.produto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.inventory_pro.exceptions.NotFoundException;
import com.example.inventory_pro.produto.dtos.ProdutoResponseDTO;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

  @InjectMocks
  private ProdutoService produtoService;

  @Mock
  private ProdutoRepository produtoRepository;

  Produto produtoMock = Produto.builder()
      .id(1L)
      .nome("Produto Test")
      .descricao("Produto Test Descricao")
      .quantidade(10)
      .valor(10)
      .build();

  @Test
  public void mustReturnProdutoListOneUser() {
    Mockito.when(produtoRepository.findAll()).thenReturn(Collections.singletonList(produtoMock));

    List<ProdutoResponseDTO> produtos = produtoService.listProdutos();

    Assertions.assertEquals(1, produtos.size());
  }

  @Test
  public void mustReturnProduto() {
    Mockito.when(produtoRepository.findById(1L)).thenReturn(java.util.Optional.of(produtoMock));

    Produto produto = produtoService.getProdutoById(1L);
    Assertions.assertEquals(produtoMock.getNome(), produto.getNome());
    Assertions.assertEquals(produtoMock.getDescricao(), produto.getDescricao());
    Assertions.assertEquals(produtoMock.getQuantidade(), produto.getQuantidade());
    Assertions.assertEquals(produtoMock.getValor(), produto.getValor());

  }

  @Test
  void mustThrowExceptionWhenProdutoNotFound() {
    Mockito.when(produtoRepository.findById(999L))
        .thenReturn(Optional.empty());

    RuntimeException exception = Assertions.assertThrows(NotFoundException.class, () -> {
      produtoService.getProdutoById(999L);
    });

    Assertions.assertEquals("Produto id:" + 999L + " não encontrado", exception.getMessage());
  }

  @Test
  void mustDeleteProdutoWhenExists() {
    Long id = 1L;

    Mockito.when(produtoRepository.findById(id))
        .thenReturn(Optional.of(produtoMock));

    produtoService.deleteProduto(id);

    Mockito.verify(produtoRepository, Mockito.times(1)).deleteById(id);
  }

  @Test
  void mustThrowExceptionWhenDeleteProdutoDoesNotExist() {
    Long id = 999L;

    Mockito.when(produtoRepository.findById(id))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(NotFoundException.class, () -> {
      produtoService.deleteProduto(id);
    });

    Mockito.verify(produtoRepository, Mockito.never()).deleteById(Mockito.any());
  }

  @Test
  void mustUpdateQuantidadeSuccessfully() {
    Long id = 1L;
    Integer quantidadeRemover = 3;

    Mockito.when(produtoRepository.findById(id))
        .thenReturn(Optional.of(produtoMock));

    Produto result = produtoService.updateQuantidade(id, quantidadeRemover);

    Assertions.assertEquals(7, result.getQuantidade()); // 10 - 3

    Mockito.verify(produtoRepository).save(produtoMock);
  }

  @Test
  void mustThrowExceptionWhenUpdateQuantidadeDoesNotExist() {
    Long id = 999L;

    Mockito.when(produtoRepository.findById(id))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(NotFoundException.class, () -> {
      produtoService.updateQuantidade(id, 3);
    });

    Mockito.verify(produtoRepository, Mockito.never()).deleteById(Mockito.any());
  }

}
