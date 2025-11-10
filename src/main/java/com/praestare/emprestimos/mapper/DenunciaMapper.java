package com.praestare.emprestimos.mapper;

import java.time.YearMonth;

import com.praestare.emprestimos.model.Denuncia;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.DenunciaDto;
import com.praestare.emprestimos.model.dto.DenunciaResponseDto;
import com.praestare.emprestimos.service.TaxaMercadoService;

public class DenunciaMapper {

    public static Denuncia toEntity(DenunciaDto dto, Usuario usuario) {
        Denuncia denuncia = new Denuncia();

        denuncia.setBanco(dto.getBanco());
        denuncia.setTaxa(dto.getTaxa());
        denuncia.setValor(dto.getValor());
        denuncia.setPrazo(dto.getPrazo());
        denuncia.setAnonimo(dto.isAnonimo());
        denuncia.setDescricao(dto.getDescricao());
        denuncia.setUsuario(dto.isAnonimo() ? null : usuario);
        return denuncia;
    }

    public static DenunciaDto toDto(Denuncia denuncia) {
        DenunciaDto dto = new DenunciaDto();

        dto.setBanco(denuncia.getBanco());
        dto.setTaxa(denuncia.getTaxa());
        dto.setValor(denuncia.getValor());
        dto.setPrazo(denuncia.getPrazo());
        dto.setAnonimo(denuncia.isAnonimo());
        dto.setDescricao(denuncia.getDescricao());

        if (!denuncia.isAnonimo() && denuncia.getUsuario() != null) {
            dto.setUsuarioId(denuncia.getUsuario().getId());
        }

        return dto;
    }

    public static DenunciaResponseDto toResponseDto(Denuncia denuncia, TaxaMercadoService taxaMercadoService) {
        DenunciaResponseDto dto = new DenunciaResponseDto();

        dto.setBanco(denuncia.getBanco());
        dto.setTaxa(denuncia.getTaxa());
        dto.setValor(denuncia.getValor());
        dto.setPrazo(denuncia.getPrazo());
        dto.setAnonimo(denuncia.isAnonimo());
        dto.setDescricao(denuncia.getDescricao());
        dto.setUsuarioId(denuncia.getUsuario() != null ? denuncia.getUsuario().getId() : null);

        String mesReferencia = YearMonth.now().toString();
        boolean taxaConfiavel = taxaMercadoService.avaliarTaxa(
            denuncia.getBanco(),
            denuncia.getValor(),
            denuncia.getTaxa(),
            denuncia.getPrazo(),
            mesReferencia
        );

        dto.setTaxaConfiavel(taxaConfiavel);
        dto.setAvaliacaoTaxa(taxaConfiavel
            ? "Taxa dentro do padrão de mercado"
            : "Taxa considerada abusiva");

        double taxaMensal = denuncia.getTaxa() / 100;
        double montanteFinal = denuncia.getValor() * Math.pow(1 + taxaMensal, denuncia.getPrazo());
        dto.setMontanteFinalCalculado(montanteFinal);

        return dto;
    }

    public static void updateEntity(Denuncia denuncia, DenunciaDto dto, Usuario usuario) {
        denuncia.setBanco(dto.getBanco());
        denuncia.setTaxa(dto.getTaxa());
        denuncia.setValor(dto.getValor());
        denuncia.setPrazo(dto.getPrazo());
        denuncia.setAnonimo(dto.isAnonimo());
        denuncia.setDescricao(dto.getDescricao());

        if (!dto.isAnonimo()) {
            denuncia.setUsuario(usuario);
        } else {
            denuncia.setUsuario(null);
        }
    }

    public static DenunciaResponseDto toResponseDto(Denuncia denuncia) {
        DenunciaResponseDto dto = new DenunciaResponseDto();
        dto.setDescricao(denuncia.getDescricao());
        dto.setUsuarioId(denuncia.getUsuario().getId());
        return dto;
    }

}

