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

  public ProdutoResponseDTO createProduto(ProdutoCreateDTO produtoCreateDTO) {
    Produto produto = produtoRepository.save(new Produto(produtoCreateDTO));
    return new ProdutoResponseDTO(produto);
  }

  public Produto getProdutoById(Long id) {
    return produtoRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Produto id:" + id + " não encontrado"));
  }

  public ProdutoResponseDTO updateProduto(Long id, ProdutoUpdateDTO produtoUpdateDTO) {
    Produto produto = getProdutoById(id);

    if (produtoUpdateDTO.getNome() != null)
      produto.setNome(produtoUpdateDTO.getNome());

    if (produtoUpdateDTO.getDescricao() != null)
      produto.setDescricao(produtoUpdateDTO.getDescricao());

    if (produtoUpdateDTO.getValor() != null)
      produto.setValor(produtoUpdateDTO.getValor());

    return new ProdutoResponseDTO(produtoRepository.save(produto));

  }

  public void deleteProduto(Long id) {
    getProdutoById(id);
    produtoRepository.deleteById(id);
  }

}
