package com.praestare.emprestimos.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.ContatoDto;
import com.praestare.emprestimos.model.dto.ContatoResponseDto;


public class ContatoMapper {

    public static ContatoResponseDto toDTO(Contato contato) {
        if (contato == null) return null;
        ContatoResponseDto dto = new ContatoResponseDto();
        dto.setId(contato.getId());
        dto.setTelefone(contato.getTelefone());
        dto.setEmail(contato.getEmail());
        dto.setBanco(contato.getBanco());
        dto.setUsuarioId(contato.getUsuario() != null ? contato.getUsuario().getId() : null);
        return dto;
    }

    public static List<ContatoResponseDto> toDTOList(List<Contato> contatos) {
        if (contatos == null) {
            return Collections.emptyList();
        }
        return contatos.stream()
            .map(ContatoMapper::toDTO)
            .collect(Collectors.toList());
    }

    public static Contato toEntity(ContatoDto dto, Usuario usuario) {
        if (dto == null) return null;
        Contato contato = new Contato();
        contato.setTelefone(dto.getTelefone());
        contato.setEmail(dto.getEmail());
        contato.setBanco(dto.getBanco());
        contato.setUsuario(usuario);
        return contato;
    }

    public static void updateEntityFromDto(Contato contato, ContatoDto dto, Usuario usuario) {
        if (contato == null || dto == null) return;
        contato.setTelefone(dto.getTelefone());
        contato.setEmail(dto.getEmail());
        contato.setBanco(dto.getBanco());
        contato.setUsuario(usuario);
    }
}




