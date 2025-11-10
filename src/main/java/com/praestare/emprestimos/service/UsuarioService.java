package com.praestare.emprestimos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.praestare.emprestimos.mapper.ContatoMapper;
import com.praestare.emprestimos.mapper.UsuarioMapper;
import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.ContatoResponseDto;
import com.praestare.emprestimos.model.dto.UsuarioDto;
import com.praestare.emprestimos.model.dto.UsuarioResponseDto;
import com.praestare.emprestimos.repository.ContatoRepository;
import com.praestare.emprestimos.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ContatoService contatoService;

    public Page<UsuarioResponseDto> listarTodos(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarios.map(UsuarioMapper::toDTO);
    }

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

    public Page<ContatoResponseDto> listarContatosPorUsuario(Long id, Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado"));

        Page<Contato> contatos = contatoRepository.findByUsuario(usuario, pageable);
        return contatos.map(ContatoMapper::toResponseDto);
    }


    public Usuario atualizarUsuario(Long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));

        if (dto.getContatos() != null) {
            List<Contato> contatosAtualizados = contatoService.atualizarContato(dto.getContatos(), usuario);
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


