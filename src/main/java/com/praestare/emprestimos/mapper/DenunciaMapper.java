package com.praestare.emprestimos.mapper;

import com.praestare.emprestimos.model.Denuncia;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.DenunciaDto;
import com.praestare.emprestimos.model.dto.DenunciaResponseDto;

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

    public static DenunciaResponseDto toResponseDto(Denuncia denuncia) {
        DenunciaResponseDto dto = new DenunciaResponseDto();
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

}

