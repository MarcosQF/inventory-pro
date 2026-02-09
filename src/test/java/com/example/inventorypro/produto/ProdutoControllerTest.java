package com.example.inventorypro.produto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.inventorypro.produto.dtos.ProdutoCreateDTO;
import com.example.inventorypro.produto.dtos.ProdutoResponseDTO;
import com.example.inventorypro.produto.dtos.ProdutoUpdateDTO;
import com.example.inventorypro.infra.SecurityFilter;
import com.example.inventorypro.infra.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ProdutoController.class)
@AutoConfigureMockMvc(addFilters = false) // DESATIVA TODOS OS FILTROS DE SEGURANÇA NO TESTE
class ProdutoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProdutoService produtoService;

  @MockBean
  private SecurityFilter securityFilter;

  @MockBean
  private TokenService tokenService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void mustCreateProduto() throws Exception {
    ProdutoCreateDTO createDTO = ProdutoCreateDTO.builder()
        .nome("Braço Bionico")
        .descricao("é um braço bionico")
        .valor(120000)
        .quantidade(10)
        .build();

    ProdutoResponseDTO responseDTO = ProdutoResponseDTO.builder()
        .id(34L)
        .nome("Braço Bionico")
        .descricao("é um braço bionico")
        .valor("R$ 1.200,00")
        .quantidade(10)
        .build();

    when(produtoService.createProduto(any(ProdutoCreateDTO.class))).thenReturn(responseDTO);

    mockMvc.perform(post("/api/produtos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.nome").value("Braço Bionico"))
        .andExpect(jsonPath("$.valor").value("R$ 1.200,00"));
  }

  @Test
  void mustListAllProdutos() throws Exception {
    ProdutoResponseDTO produto = ProdutoResponseDTO.builder()
        .id(1L)
        .nome("Teclado")
        .descricao("Mecânico")
        .valor("R$ 350,00")
        .quantidade(5)
        .build();

    when(produtoService.listProdutos()).thenReturn(List.of(produto));

    mockMvc.perform(get("/api/produtos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].nome").value("Teclado"));
  }

  @Test
  void mustDeleteProduto() throws Exception {
    doNothing().when(produtoService).deleteProduto(1L);

    mockMvc.perform(delete("/api/produtos/1"))
        .andExpect(status().isNoContent());
  }

  @Test
  void mustUpdateProduto() throws Exception {
    ProdutoUpdateDTO updateDTO = ProdutoUpdateDTO.builder()
        .nome("Teclado Novo")
        .descricao("Switch Red")
        .valor(35000)
        .quantidade(8)
        .build();

    ProdutoResponseDTO responseDTO = ProdutoResponseDTO.builder()
        .id(1L)
        .nome("Teclado Novo")
        .descricao("Switch Red")
        .valor("R$ 350,00")
        .quantidade(8)
        .build();

    when(produtoService.updateProduto(eq(1L), any(ProdutoUpdateDTO.class))).thenReturn(responseDTO);

    mockMvc.perform(patch("/api/produtos/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Teclado Novo"));
  }
}
