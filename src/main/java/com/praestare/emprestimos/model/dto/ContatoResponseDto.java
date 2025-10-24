package com.praestare.emprestimos.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoResponseDto {
    
    private Long id;
    private String telefone;
    private String email;
    private String banco;
    private Long usuarioId;
}
