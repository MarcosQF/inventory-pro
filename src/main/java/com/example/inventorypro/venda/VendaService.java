package com.example.inventorypro.venda;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.inventorypro.exceptions.NotFoundException;
import com.example.inventorypro.produto.Produto;
import com.example.inventorypro.produto.ProdutoService;
import com.example.inventorypro.usuarios.Usuario;
import com.example.inventorypro.venda.dtos.VendaCreateDTO;
import com.example.inventorypro.venda.dtos.VendaItemCreateDTO;
import com.example.inventorypro.venda.dtos.VendaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendaService {

  private final VendaRepository vendaRepository;
  private final ProdutoService produtoService;

  @Transactional
  public VendaResponseDTO criarVenda(VendaCreateDTO dto) {

    Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    Venda venda = new Venda();
    venda.setDataVenda(LocalDateTime.now());
    venda.setUsuario(usuarioLogado);
    venda.setItens(new ArrayList<>());

    List<VendaItem> itens = new ArrayList<>();

    for (VendaItemCreateDTO itemDTO : dto.getItens()) {
      Produto produto = produtoService.getProdutoById(itemDTO.getProdutoId());

      produtoService.updateQuantidade(produto.getId(), itemDTO.getQuantidade());

      VendaItem item = VendaItem.builder()
          .produto(produto)
          .venda(venda)
          .quantidade(itemDTO.getQuantidade())
          .build();

      item.calcularTotal();
      itens.add(item);
    }

    venda.setItens(itens);
    venda.calcularTotal();

    Venda vendaSalva = vendaRepository.save(venda);
    return new VendaResponseDTO(vendaSalva);
  }

  public List<VendaResponseDTO> listarVendas() {
    return vendaRepository.findAll().stream()
        .map(VendaResponseDTO::new)
        .toList();
  }

  public VendaResponseDTO buscarVenda(Long id) {
    Venda venda = vendaRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Venda não encontrada: " + id));

    return new VendaResponseDTO(venda);
  }
}
