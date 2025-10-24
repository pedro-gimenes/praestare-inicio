package com.praestare.emprestimos.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoDto {
    
    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;
    
    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "Obrigatório nome da instituição financeira")
    private String Banco;
    private Long usuarioId;
    }
