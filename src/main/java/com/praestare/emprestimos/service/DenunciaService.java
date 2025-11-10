package com.praestare.emprestimos.service;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private TaxaMercadoService taxaMercadoService;

    public Denuncia criarDenuncia(DenunciaDto dto){
        String mesReferencia = YearMonth.now().toString();
        boolean taxaConfiavel = taxaMercadoService.avaliarTaxa(
            dto.getBanco(), dto.getValor(), dto.getTaxa(), dto.getPrazo(), mesReferencia
        );

        if (!taxaConfiavel) {
            throw new EntityNotFoundException("Taxa acima do padrão de mercado para o banco informado.");
        }
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new EntityNotFoundException("Denuncia não encontrada: " + dto.getUsuarioId()));
        Denuncia denuncia = DenunciaMapper.toEntity(dto, usuario);
        denuncia.setUsuario(dto.isAnonimo() ? null : usuario);
        return denunciaRepository.save(denuncia);
    }

    public Page<DenunciaResponseDto> listarDenuncias(Pageable pageable) {
        Page<Denuncia> denuncias = denunciaRepository.findAll(pageable);
        return denuncias.map(denuncia -> DenunciaMapper.toResponseDto(denuncia, taxaMercadoService));
    }

    public Page<DenunciaResponseDto> listarDenunciasPorUsuario(Long usuarioId, Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + usuarioId + " não encontrado"));

        Page<Denuncia> denuncias = denunciaRepository.findByUsuario(usuario, pageable);

        return denuncias.map(denuncia -> DenunciaMapper.toResponseDto(denuncia, taxaMercadoService));
    }

    public Denuncia atualizarDenuncia(Long id, DenunciaDto dto) {
        Denuncia denunciaExistente = denunciaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Denúncia com ID " + id + " não encontrada"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + dto.getUsuarioId() + " não encontrado"));
        String mesReferencia = YearMonth.now().toString();

        boolean taxaConfiavel = taxaMercadoService.avaliarTaxa(
            dto.getBanco(), dto.getValor(), dto.getTaxa(), dto.getPrazo(), mesReferencia
        );

        if (!taxaConfiavel) {
            throw new EntityNotFoundException("A taxa informada está acima do padrão de mercado para o banco informado.");
        }
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
