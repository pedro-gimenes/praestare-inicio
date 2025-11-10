package com.praestare.emprestimos.mapper;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
        if (usuario == null) return null;

        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setName(usuario.getName());
        dto.setCpf(usuario.getCpf());

        List<ContatoResponseDto> contatosDtoList = contatos.stream()
        .map(ContatoMapper::toResponseDto)
        .collect(Collectors.toList());

        Page<ContatoResponseDto> contatosDtoPage = new PageImpl<>(contatosDtoList);
        dto.setContatos(contatosDtoPage);

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


