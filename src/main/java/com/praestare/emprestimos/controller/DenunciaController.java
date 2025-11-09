package com.praestare.emprestimos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.praestare.emprestimos.mapper.DenunciaMapper;
import com.praestare.emprestimos.model.Denuncia;
import com.praestare.emprestimos.model.dto.DenunciaDto;
import com.praestare.emprestimos.model.dto.DenunciaResponseDto;
import com.praestare.emprestimos.service.DenunciaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/denuncias")
public class DenunciaController {
    
    @Autowired
    private DenunciaService denunciaService;
    
    @PostMapping
    public ResponseEntity<DenunciaDto> criarDenuncia(@RequestBody @Valid DenunciaDto dto) {
        Denuncia denuncia = denunciaService.criarDenuncia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(DenunciaMapper.toDto(denuncia));
    }

    @GetMapping
    public ResponseEntity<List<DenunciaResponseDto>> listarDenuncias() {
        List<DenunciaResponseDto> lista = denunciaService.listarDenuncias();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DenunciaResponseDto>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<DenunciaResponseDto> denuncias = denunciaService.listarDenunciasPorUsuario(usuarioId);
        return ResponseEntity.ok(denuncias);
}


}
