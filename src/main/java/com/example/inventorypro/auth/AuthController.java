package com.example.inventorypro.auth;

import com.example.inventorypro.auth.dtos.LoginRequestDTO;
import com.example.inventorypro.auth.dtos.RegisterRequestDTO;
import com.example.inventorypro.auth.dtos.ResponseDTO;
import com.example.inventorypro.infra.TokenService;
import com.example.inventorypro.usuarios.Usuario;
import com.example.inventorypro.usuarios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final UsuarioRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
    Usuario user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
    if (passwordEncoder.matches(body.password(), user.getPassword())) {
      String token = this.tokenService.generateToken(user);
      return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
    }
    return ResponseEntity.badRequest().build();
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body) {
    Optional<Usuario> user = this.repository.findByEmail(body.email());

    if (user.isEmpty()) {
      Usuario newUser = new Usuario();
      newUser.setPassword(passwordEncoder.encode(body.password()));
      newUser.setEmail(body.email());
      newUser.setName(body.name());
      this.repository.save(newUser);

      String token = this.tokenService.generateToken(newUser);
      return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
    }
    return ResponseEntity.badRequest().build();
  }
}
