package com.example.inventorypro.usuarios;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventorypro.usuarios.dtos.UsuarioResponseDTO;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

  private final UsuarioService usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @GetMapping("/me")
  public ResponseEntity<UsuarioResponseDTO> getMeuPerfil(@AuthenticationPrincipal Usuario usuarioLogado) {
    if (usuarioLogado == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    UsuarioResponseDTO response = UsuarioResponseDTO.builder()
        .id(usuarioLogado.getId())
        .name(usuarioLogado.getName())
        .email(usuarioLogado.getEmail())
        .build();

    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
    List<UsuarioResponseDTO> usuarios = usuarioService.findAll();
    return ResponseEntity.ok(usuarios);
  }
}
