package com.praestare.emprestimos.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.praestare.emprestimos.mapper.UsuarioMapper;
import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.ContatoResponseDto;
import com.praestare.emprestimos.model.dto.UsuarioDto;
import com.praestare.emprestimos.model.dto.UsuarioResponseDto;
import com.praestare.emprestimos.repository.ContatoRepository;
import com.praestare.emprestimos.repository.UsuarioRepository;
import com.praestare.emprestimos.service.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody @Valid UsuarioDto dto) {
        Usuario usuarioSalvo = usuarioService.salvarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }
    
    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDto>> listarTodos(Pageable pageable) {
        Page<UsuarioResponseDto> usuarios = usuarioService.listarTodos(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("\"/{id}/usuarios\"")
    public Page<ContatoResponseDto> listarContatosPorUsuario(Long id, Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado"));
        Page<Contato> contatos = contatoRepository.findByUsuario(usuario, pageable);
        return contatos.map(ContatoMapper::toResponseDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioDto dto) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, dto);
        UsuarioResponseDto responseDto = UsuarioMapper.toDTO(usuarioAtualizado);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @Valid Long id) {
        usuarioService.deletarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }


}



