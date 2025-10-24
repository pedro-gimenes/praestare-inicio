package com.praestare.emprestimos.model.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDto {

    @NotBlank(message = "Necessário id não ser nulo")
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private List<ContatoResponseDto> contatos;

}
