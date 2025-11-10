package com.praestare.emprestimos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.praestare.emprestimos.mapper.DenunciaMapper;
import com.praestare.emprestimos.model.Denuncia;
import com.praestare.emprestimos.model.dto.DenunciaDto;
import com.praestare.emprestimos.model.dto.DenunciaResponseDto;
import com.praestare.emprestimos.service.DenunciaService;
import com.praestare.emprestimos.service.TaxaMercadoService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/denuncias")
public class DenunciaController {
    
    @Autowired
    private DenunciaService denunciaService;

    @Autowired
    private TaxaMercadoService taxaMercadoService;
    
    @PostMapping
    public ResponseEntity<DenunciaResponseDto> criarDenuncia(@RequestBody @Valid DenunciaDto dto) {
        Denuncia denuncia = denunciaService.criarDenuncia(dto);
        DenunciaResponseDto responseDto = DenunciaMapper.toResponseDto(denuncia, taxaMercadoService);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<Page<DenunciaResponseDto>> listarDenuncias(Pageable pageable) {
        Page<DenunciaResponseDto> pagina = denunciaService.listarDenuncias(pageable);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<DenunciaResponseDto>> listarDenunciasPorUsuario(@PathVariable Long usuarioId,Pageable pageable) {
        Page<DenunciaResponseDto> pagina = denunciaService.listarDenunciasPorUsuario(usuarioId, pageable);
        return ResponseEntity.ok(pagina);
    }

    @PutMapping("{/id}")
    public ResponseEntity<DenunciaResponseDto> atualizarDenuncia(@PathVariable Long id , @RequestBody @Valid DenunciaDto dto) {
        Denuncia denunciaAtualizada = denunciaService.atualizarDenuncia(id, dto);
        DenunciaResponseDto responseDto = DenunciaMapper.toResponseDto(denunciaAtualizada, taxaMercadoService);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @Valid Long id) {
        denunciaService.deletarDenuncia(id);
        return ResponseEntity.noContent().build();
    }


}
