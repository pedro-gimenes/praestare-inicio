package com.praestare.emprestimos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "Denuncia")
public class Denuncia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "Instituição financeira obrigatória")
    @Column(nullable = false)
    private String banco;

    @NotNull(message = "Taxa é obrigatória")
    @Column(nullable = false)
    @DecimalMin("0.1")
    @DecimalMax("100.0")
    private Double taxa;

    @NotNull(message = "Valor é obrigatório")
    @Column(nullable = false)
    @DecimalMin("1.0")
    private Double valor;

    @NotNull(message = "Prazo é obrigatório")
    @Column(nullable = false)
    @Min(value = 1, message = "Prazo mínimo é 1 mês")
    private int prazo;

    @NotNull(message = "Campo obrigatório")
    @Column(nullable = false)
    private boolean anonimo;

    @Column
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    @Column
    private boolean taxaConfiavel;

    @Column
    private String avaliacaoTaxa;

    @Column
    private Double montanteFinalCalculado;

}
