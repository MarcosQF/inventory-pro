package com.example.inventorypro.produto;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.inventorypro.produto.dtos.ProdutoCreateDTO;
import com.example.inventorypro.produto.dtos.ProdutoResponseDTO;
import com.example.inventorypro.produto.dtos.ProdutoUpdateDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produto")
@SecurityRequirement(name = "bearer-key")
public class ProdutoController {

  @Autowired
  private ProdutoService produtoService;

  @PostMapping
  public ResponseEntity<ProdutoResponseDTO> createProduto(@Valid @RequestBody ProdutoCreateDTO produtoCreateDTO) {
    ProdutoResponseDTO produtoResponseDTO = produtoService.createProduto(produtoCreateDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponseDTO);
  }

  @GetMapping
  public ResponseEntity<List<ProdutoResponseDTO>> listAll() {
    List<ProdutoResponseDTO> produtos = produtoService.listProdutos();
    return ResponseEntity.ok(produtos);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    produtoService.deleteProduto(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ProdutoResponseDTO> update(@PathVariable Long id,
      @Valid @RequestBody ProdutoUpdateDTO produtoUpdateDTO) {
    ProdutoResponseDTO produtoResponseDTO = produtoService.updateProduto(id, produtoUpdateDTO);
    return ResponseEntity.ok(produtoResponseDTO);
  }
}
