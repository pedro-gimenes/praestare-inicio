package com.praestare.emprestimos.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoResponseDto {
    
    @NotNull(message = "Necessário id não ser nulo")
    private Long id;
    private String telefone;
    private String email;
    private String banco;
    private Long usuarioId;
}
