package com.praestare.emprestimos.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.ContatoDto;
import com.praestare.emprestimos.model.dto.ContatoResponseDto;
import com.praestare.emprestimos.model.dto.UsuarioDto;
import com.praestare.emprestimos.model.dto.UsuarioResponseDto;


@Component
public class UsuarioMapper {

    public static UsuarioResponseDto toDTO(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setName(usuario.getName());
        dto.setCpf(usuario.getCpf());

        List<ContatoResponseDto> contatos = Optional.ofNullable(usuario.getContatos())
            .orElse(Collections.emptyList())
            .stream()
            .map(UsuarioMapper::toContatoDTO)
            .collect(Collectors.toList());

        dto.setContatos(contatos);
        return dto;
    }

        public static ContatoResponseDto toContatoDTO(Contato contato) {
        ContatoResponseDto dto = new ContatoResponseDto();
        dto.setId(contato.getId());
        dto.setTelefone(contato.getTelefone());
        dto.setEmail(contato.getEmail());
        dto.setBanco(contato.getBanco());

        if (contato.getUsuario() != null) {
            dto.setUsuarioId(contato.getUsuario().getId());
        }

        return dto;
    }

    public static Usuario toEntity(UsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setName(dto.getName());
        usuario.setCpf(dto.getCpf());
        return usuario;
    }

    public static Contato toContatoEntity(ContatoDto dto) {
        Contato contato = new Contato();
        contato.setTelefone(dto.getTelefone());
        contato.setEmail(dto.getEmail());
        contato.setBanco(dto.getBanco());
        return contato;
    }
}


