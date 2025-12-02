package com.example.inventorypro.produto;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.inventorypro.produto.dtos.ProdutoCreateDTO;
import com.example.inventorypro.produto.dtos.ProdutoResponseDTO;
import com.example.inventorypro.produto.dtos.ProdutoUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProdutoService produtoService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void mustCreateProduto() throws Exception {

    ProdutoCreateDTO createDTO = ProdutoCreateDTO.builder()
        .nome("Mouse Gamer")
        .descricao("RGB")
        .valor(15000)
        .quantidade(10)
        .build();

    ProdutoResponseDTO responseDTO = ProdutoResponseDTO.builder()
        .id(1L)
        .nome("Mouse Gamer")
        .descricao("RGB")
        .valor("R$ 150,00")
        .quantidade(10)
        .build();

    when(produtoService.createProduto(createDTO)).thenReturn(responseDTO);

    mockMvc.perform(post("/api/produtos/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.nome").value("Mouse Gamer"));
  }

  @Test
  void mustListAllProdutos() throws Exception {

    ProdutoResponseDTO produto = ProdutoResponseDTO.builder()
        .id(1L)
        .nome("Teclado")
        .descricao("Mecânico")
        .valor("RS $ 350,00")
        .quantidade(5)
        .build();

    when(produtoService.listProdutos()).thenReturn(List.of(produto));

    mockMvc.perform(get("/api/produtos/"))
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

    when(produtoService.updateProduto(1L, updateDTO)).thenReturn(responseDTO);

    mockMvc.perform(patch("/api/produtos/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Teclado Novo"));
  }
}
