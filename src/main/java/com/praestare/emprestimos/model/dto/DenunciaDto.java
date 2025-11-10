package com.praestare.emprestimos.model.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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

    @DecimalMin("0.1")
    @DecimalMax("100.0")
    @NotNull(message = "Taxa é obrigatória")
    private Double taxa;

    @DecimalMin("1.0")
    @NotNull(message = "Valor é obrigatório")
    private Double valor;

    @Min(value = 1, message = "Prazo mínimo é 1 mês")
    @NotNull(message = "Prazo é obrigatório")
    private int prazo;

    @NotNull(message = "Definição é obrigatória")
    private boolean anonimo;

    private String descricao;

    @NotBlank(message = "Obrigatório uso do UsuarioId")
    private Long usuarioId;

    private boolean taxaConfiavel;

    private String avaliacaoTaxa;

    private Double montanteFinalCalculado;


}
