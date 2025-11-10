package com.praestare.emprestimos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.praestare.emprestimos.mapper.DenunciaMapper;
import com.praestare.emprestimos.model.Denuncia;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.DenunciaDto;
import com.praestare.emprestimos.model.dto.DenunciaResponseDto;
import com.praestare.emprestimos.repository.DenunciaRepository;
import com.praestare.emprestimos.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class DenunciaService {

    @Autowired
    private DenunciaRepository denunciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Denuncia criarDenuncia(DenunciaDto dto){
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new EntityNotFoundException("Denuncia não encontrada: " + dto.getUsuarioId()));
        Denuncia denuncia = DenunciaMapper.toEntity(dto, usuario);
        if (!denuncia.isAnonimo()) {
            denuncia.setUsuario(usuario);
        } else {
            denuncia.setUsuario(null);
        }
        return denunciaRepository.save(denuncia);
    }

    public List<DenunciaResponseDto> listarDenuncias() {
        List<Denuncia> denuncias = denunciaRepository.findAll();
        return denuncias.stream()
            .map(DenunciaMapper::toResponseDto)
            .collect(Collectors.toList());
    }

    public List<DenunciaResponseDto> listarDenunciasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + usuarioId));

        List<Denuncia> denuncias = denunciaRepository.findByUsuarioId(usuario);
        return denuncias.stream()
            .map(DenunciaMapper::toResponseDto)
            .collect(Collectors.toList());
    }

    public Denuncia atualizarDenuncia(Long id, DenunciaDto dto) {
        Denuncia denunciaExistente = denunciaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Denúncia com ID " + id + " não encontrada"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + dto.getUsuarioId() + " não encontrado"));

        DenunciaMapper.updateEntity(denunciaExistente, dto, usuario);

        return denunciaRepository.save(denunciaExistente);
    }

    public void deletarDenuncia(Long id){
        if(!denunciaRepository.existsById(id)){
            throw new EntityNotFoundException("Usuário com ID " + id + " não encontrado");
        }
        denunciaRepository.deleteById(id);
    }
}
