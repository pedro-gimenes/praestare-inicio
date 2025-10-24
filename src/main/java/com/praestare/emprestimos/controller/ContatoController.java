package com.praestare.emprestimos.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.ContatoDto;
import com.praestare.emprestimos.repository.ContatoRepository;
import com.praestare.emprestimos.repository.UsuarioRepository;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/contatos")
public class ContatoController {
    
    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping()
    public ResponseEntity<?> criar(@RequestBody @Valid ContatoDto dto) {
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(dto.getUsuarioId());
        if(usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente não encontrado");
        }

        Contato contato = new Contato();
        contato.setTelefone(dto.getTelefone());
        contato.setEmail(dto.getEmail());
        contato.setBanco(dto.getBanco());
        contato.setUsuario(usuarioOpt.get());
        contatoRepository.save(contato);

        return ResponseEntity.status(HttpStatus.CREATED).body(contato);
    }
    
}
