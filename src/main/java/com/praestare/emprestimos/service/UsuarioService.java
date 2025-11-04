package com.praestare.emprestimos.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praestare.emprestimos.mapper.UsuarioMapper;
import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.ContatoResponseDto;
import com.praestare.emprestimos.model.dto.UsuarioDto;
import com.praestare.emprestimos.model.dto.UsuarioResponseDto;
import com.praestare.emprestimos.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodosUsuarios() {
        return this.usuarioRepository.findAll();
    }

    public Usuario salvarUsuario(UsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setName(dto.getName());

        if(dto.getContatos() != null && dto.getContatos().size() > 0) {
            List<Contato> contatos = dto.getContatos().stream().map(c -> {
                Contato contato = new Contato();
                contato.setTelefone(c.getTelefone());
                contato.setEmail(c.getEmail());
                contato.setBanco(c.getBanco());
                contato.setUsuario(usuario);
                return contato;
            }).collect(Collectors.toList());
            usuario.setContatos(contatos);
        }
        return usuarioRepository.save(usuario);
    }

    public List<UsuarioResponseDto> listarTodos() {
    return usuarioRepository.findAll().stream()
        .map(UsuarioMapper::toDTO)
        .collect(Collectors.toList());
    }

    public List<ContatoResponseDto> listarContatosPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

    return Optional.ofNullable(usuario.getContatos())
        .orElse(Collections.emptyList())
        .stream()
        .map(UsuarioMapper::toContatoDTO)
        .collect(Collectors.toList());

    }

    public Usuario atualizarUsuario(Long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));

        usuario.setName(dto.getName());

        if (dto.getContatos() != null) {
            List<Contato> contatosAtualizados = atualizarContatos(dto.getContatos(), usuario);
            usuario.setContatos(contatosAtualizados);
        }

        return usuarioRepository.save(usuario);
    }

    public void deletarUsuarioPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

}


