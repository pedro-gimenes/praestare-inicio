package com.praestare.emprestimos.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.praestare.emprestimos.mapper.ContatoMapper;
import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.dto.ContatoDto;
import com.praestare.emprestimos.model.dto.ContatoResponseDto;
import com.praestare.emprestimos.repository.UsuarioRepository;
import com.praestare.emprestimos.service.ContatoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contatos")
@RequiredArgsConstructor
public class ContatoController {

    
    private ContatoService contatoService;
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<ContatoResponseDto> criar(@RequestBody @Valid ContatoDto dto) {
            Contato contato = contatoService.criarContato(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ContatoMapper.toDTO(contato));

    }
    @GetMapping("/usuario/{usuarioId}/contatos")
    public ResponseEntity<Page<ContatoResponseDto>> listarContatos(@PathVariable Long usuarioId, Pageable pageable) {
        usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new EntityNotFoundException( "Usuário não encontrado"));

        Page<Contato> contatos = contatoService.listarContatos(usuarioId, pageable);
        Page<ContatoResponseDto> response = contatos.map(ContatoMapper::toResponseDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoResponseDto> atualizar(@PathVariable Long id, @RequestBody @Valid ContatoDto dto) {
        Contato contatoAtualizado = contatoService.atualizarContato(id, dto);
        ContatoResponseDto responseDto = ContatoMapper.toResponseDto(contatoAtualizado);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable  @Valid Long id) {
        contatoService.deletarContato(id);
        return ResponseEntity.noContent().build();
    }



}

