package com.praestare.emprestimos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.ContatoDto;
import com.praestare.emprestimos.model.dto.ContatoResponseDto;
import com.praestare.emprestimos.model.dto.UsuarioDto;
import com.praestare.emprestimos.model.dto.UsuarioResponseDto;
import com.praestare.emprestimos.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
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
        return usuarioRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ContatoResponseDto> listarContatosPorUsuario(Long UsuarioId) {
        Usuario usuario = usuarioRepository.findById(UsuarioId)
                            .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        return usuario.getContatos().stream().map(c -> {
            ContatoResponseDto dto = new ContatoResponseDto();
            dto.setId(c.getId());
            dto.setTelefone(c.getTelefone());
            dto.setEmail(c.getEmail());
            dto.setBanco(c.getBanco());
            return dto;
        }).collect(Collectors.toList());
        
    }

    private UsuarioResponseDto toDTO(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setName(usuario.getName());
        dto.setCpf(usuario.getCpf());
        
        List<ContatoResponseDto> contatos = usuario.getContatos().stream().map(c -> {
            ContatoResponseDto contatoDto = new ContatoResponseDto();
            contatoDto.setId(c.getId());
            contatoDto.setTelefone(c.getTelefone());
            contatoDto.setEmail(c.getEmail());
            contatoDto.setBanco(c.getBanco());
            return contatoDto;
        }).collect(Collectors.toList());
        dto.setContatos(contatos);
        return dto;
    }

public Usuario atualizarUsuario(Long id, UsuarioDto dto) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));
    usuario.setName(dto.getName());

    if (dto.getContatos() != null) {
        Map<Long, Contato> contatosExistentes = usuario.getContatos().stream()
            .filter(c -> c.getId() != null)
            .collect(Collectors.toMap(t -> t.getId(), c -> c));

        List<Contato> contatosAtualizados = new ArrayList<>();

        for (ContatoDto contatoDto : dto.getContatos()) {
            Contato contato;

            if (contatoDto.getId() != null && contatosExistentes.containsKey(contatoDto.getId())) {
                contato = contatosExistentes.get(contatoDto.getId());
            } else {
                contato = new Contato();
                contato.setUsuario(usuario);
            }

            contato.setTelefone(contatoDto.getTelefone());
            contato.setEmail(contatoDto.getEmail());
            contato.setBanco(contatoDto.getBanco());

            contatosAtualizados.add(contato);
        }

        usuario.setContatos(contatosAtualizados);
    }

    return usuarioRepository.save(usuario);
}

    public void deletarUsuarioPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

}


