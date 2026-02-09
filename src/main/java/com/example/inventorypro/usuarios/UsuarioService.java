package com.example.inventorypro.usuarios;

import com.example.inventorypro.usuarios.dtos.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;

  @Transactional(readOnly = true)
  public List<UsuarioResponseDTO> findAll() {
    return usuarioRepository.findAll()
        .stream()
        .map(this::toResponseDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public UsuarioResponseDTO findByEmail(String email) {
    return usuarioRepository.findByEmail(email)
        .map(this::toResponseDTO)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
  }

  private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
    return UsuarioResponseDTO.builder()
        .id(usuario.getId())
        .name(usuario.getName())
        .email(usuario.getEmail())
        .build();
  }
}
