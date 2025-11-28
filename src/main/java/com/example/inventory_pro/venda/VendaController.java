package com.example.inventory_pro.venda;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.inventory_pro.venda.dtos.VendaCreateDTO;
import com.example.inventory_pro.venda.dtos.VendaResponseDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/venda")
@RequiredArgsConstructor
@Tag(name = "Venda")
public class VendaController {

  private final VendaService vendaService;

  @PostMapping("/")
  public ResponseEntity<VendaResponseDTO> criarVenda(@Valid @RequestBody VendaCreateDTO dto) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(vendaService.criarVenda(dto));
  }

  @GetMapping
  public ResponseEntity<List<VendaResponseDTO>> listarVendas() {
    return ResponseEntity.ok(vendaService.listarVendas());
  }

  @GetMapping("/{id}")
  public ResponseEntity<VendaResponseDTO> buscarVenda(@PathVariable Long id) {
    return ResponseEntity.ok(vendaService.buscarVenda(id));
  }
}
