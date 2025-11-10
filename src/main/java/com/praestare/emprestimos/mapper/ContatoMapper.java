package com.praestare.emprestimos.mapper;

import org.springframework.stereotype.Component;

import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.ContatoDto;
import com.praestare.emprestimos.model.dto.ContatoResponseDto;

@Component
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
    public static ContatoResponseDto toResponseDto(Contato contato) {
        ContatoResponseDto dto = new ContatoResponseDto();
        dto.setId(contato.getId());
        dto.setTelefone(contato.getTelefone());
        dto.setEmail(contato.getEmail());
        dto.setBanco(contato.getBanco());
        dto.setUsuarioId(contato.getUsuario() != null ? contato.getUsuario().getId() : null);
        return dto;
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




