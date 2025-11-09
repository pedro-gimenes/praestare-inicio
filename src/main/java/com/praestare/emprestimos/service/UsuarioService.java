package com.praestare.emprestimos.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praestare.emprestimos.mapper.ContatoMapper;
import com.praestare.emprestimos.mapper.UsuarioMapper;
import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.ContatoResponseDto;
import com.praestare.emprestimos.model.dto.UsuarioDto;
import com.praestare.emprestimos.model.dto.UsuarioResponseDto;
import com.praestare.emprestimos.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContatoService contatoService;

    public Usuario salvarUsuario(UsuarioDto dto) {

    if (dto == null || dto.getName() == null || dto.getName().isBlank()) {
        throw new EntityNotFoundException("Dados do usuário não informados ou incompletos");
    } else {
        Usuario usuario = UsuarioMapper.toEntity(dto);

        List<Contato> contatos = Optional.ofNullable(dto.getContatos())
            .orElseGet(List::of)
            .stream()
            .map(contatoDto -> ContatoMapper.toEntity(contatoDto, usuario))
            .collect(Collectors.toList());

        if (!contatos.isEmpty()) {
            usuario.setContatos(new ArrayList<>(contatos));
        }

        return usuarioRepository.save(usuario);
        }
    }

    public List<UsuarioResponseDto> listarTodos() {

    List<Usuario> usuarios = usuarioRepository.findAll();

    if (usuarios == null || usuarios.isEmpty()) {
        throw new EntityNotFoundException("Nenhum usuário encontrado");
    }
    return usuarioRepository.findAll().stream()
        .map(UsuarioMapper::toDTO)
        .collect(Collectors.toList());
    }

    public List<ContatoResponseDto> listarContatosPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + usuarioId + " não encontrado."));

    return Optional.ofNullable(usuario.getContatos())
        .orElse(Collections.emptyList())
        .stream()
        .map(ContatoMapper::toDTO)
        .collect(Collectors.toList());

    }

    public Usuario atualizarUsuario(Long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));

        if (dto.getContatos() != null) {
            List<Contato> contatosAtualizados = contatoService.atualizarContatos(dto.getContatos(), usuario);
            usuario.setContatos(contatosAtualizados);
        }
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuarioPorId(Long id) {
    if (!usuarioRepository.existsById(id)) {
        throw new EntityNotFoundException("Usuário com ID " + id + " não encontrado");
    }
    usuarioRepository.deleteById(id);
    }

}


