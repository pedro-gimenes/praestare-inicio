package com.praestare.emprestimos.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.ContatoResponseDto;
import com.praestare.emprestimos.model.dto.UsuarioResponseDto;

@Component
public class UsuarioMapper {

    public static UsuarioResponseDto toDTO(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setName(usuario.getName());
        dto.setCpf(usuario.getCpf());

        List<ContatoResponseDto> contatos = usuario.getContatos().stream()
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
        return dto;
    }


}
