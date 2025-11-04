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
public class UsuarioDto {
    
    private Long id;

    @NotBlank(message = "Nome obrigatório")
    private String name;

    @NotBlank(message = "obrigatório informar o cpf")
    private String cpf;
    
    @NotBlank(message = "Obrigatório informar seu email")
    private String email;

    @NotBlank(message = "Uso de senha obrigatório")
    private String password;

    private List<ContatoDto> contatos;

    public UsuarioDto(List<ContatoDto> contatos) {
        this.contatos = contatos;
    }

    public UsuarioDto(Long id,String name,
            String cpf,
            String email,
            String password) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
    }

    
}

