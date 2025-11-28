package com.example.inventory_pro.produto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inventory_pro.exceptions.NotFoundException;
import com.example.inventory_pro.produto.dtos.ProdutoCreateDTO;
import com.example.inventory_pro.produto.dtos.ProdutoResponseDTO;
import com.example.inventory_pro.produto.dtos.ProdutoUpdateDTO;

@Service
public class ProdutoService {

  @Autowired
  private ProdutoRepository produtoRepository;

  public List<ProdutoResponseDTO> listProdutos() {
    List<Produto> produtos = produtoRepository.findAll();
    return produtos.stream().map(ProdutoResponseDTO::new).toList();
  }

  public ProdutoResponseDTO createProduto(ProdutoCreateDTO dto) {
    Produto produto = Produto.builder()
        .nome(dto.getNome())
        .descricao(dto.getDescricao())
        .valor(dto.getValor())
        .quantidade(dto.getQuantidade())
        .build();

    produtoRepository.save(produto);

    return new ProdutoResponseDTO(produto);
  }

  public Produto getProdutoById(Long id) {
    return produtoRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Produto id:" + id + " não encontrado"));
  }

  public ProdutoResponseDTO updateProduto(Long id, ProdutoUpdateDTO dto) {
    Produto produto = getProdutoById(id);

    produto.update(
        dto.getNome(),
        dto.getDescricao(),
        dto.getValor(),
        dto.getQuantidade());

    produtoRepository.save(produto);

    return new ProdutoResponseDTO(produto);
  }

  public Produto updateQuantidade(Long id, Integer quantidade) {
    Produto produto = getProdutoById(id);
    produto.setQuantidade(produto.getQuantidade() - quantidade);
    produtoRepository.save(produto);
    return produto;
  }

  public void deleteProduto(Long id) {
    getProdutoById(id);
    produtoRepository.deleteById(id);
  }
}
