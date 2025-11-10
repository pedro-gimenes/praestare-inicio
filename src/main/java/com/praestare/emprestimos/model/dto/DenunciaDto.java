package com.praestare.emprestimos.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DenunciaDto {
    
    @NotBlank(message = "Instituição financeira obrigatória")
    private String banco;

    @NotNull(message = "Taxa é obrigatória")
    private Double taxa;

    @NotNull(message = "Valor é obrigatório")
    private Double valor;

    @NotNull(message = "Prazo é obrigatório")
    private int prazo;

    @NotNull(message = "Definição é obrigatória")
    private boolean anonimo;

    private String descricao;

    @NotBlank(message = "Obrigatório uso do UsuarioId")
    private Long usuarioId;

}
