package com.praestare.emprestimos.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.praestare.emprestimos.mapper.ContatoMapper;
import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.ContatoDto;
import com.praestare.emprestimos.model.dto.ContatoResponseDto;
import com.praestare.emprestimos.repository.UsuarioRepository;
import com.praestare.emprestimos.service.ContatoService;
import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

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
    @PutMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ContatoResponseDto>> atualizarPorUsuario(@PathVariable("usuarioId")Long usuarioId,
        @RequestBody @Valid List<ContatoDto> dtos) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        List<Contato> atualizados = contatoService.atualizarContatos(dtos, usuario);
        List<ContatoResponseDto> response = ContatoMapper.toDTOList(atualizados);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoResponseDto> atualizar(@PathVariable Long id, @RequestBody @Valid ContatoDto dto) {
        Contato contatoAtualizado = contatoService.atualizarContato(id, dto);
        ContatoResponseDto responseDto = ContatoMapper.toDTO(contatoAtualizado);
        return ResponseEntity.ok(responseDto);
}



}

