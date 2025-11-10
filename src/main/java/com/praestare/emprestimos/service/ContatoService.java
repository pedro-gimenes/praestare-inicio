package com.praestare.emprestimos.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.praestare.emprestimos.mapper.ContatoMapper;
import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.ContatoDto;
import com.praestare.emprestimos.repository.ContatoRepository;
import com.praestare.emprestimos.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContatoService {
    
    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public Contato criarContato(ContatoDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + dto.getUsuarioId()));
        Contato contato = ContatoMapper.toEntity(dto, usuario);
        return contatoRepository.save(contato);
    }
    public Contato atualizarContato(Long id, ContatoDto dto) {
        Contato contato = contatoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Contato com ID " + id + " não encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + dto.getUsuarioId() + " não encontrado"));

        ContatoMapper.updateEntityFromDto(contato, dto, usuario);
        return contatoRepository.save(contato);
    }

    public Page<Contato> listarContatos(Long usuarioId, Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + usuarioId));
        return contatoRepository.findByUsuario(usuario, pageable);
    }

    public void deletarContato(Long id){
        if(!contatoRepository.existsById(id)){
            throw new EntityNotFoundException("Usuário com ID " + id + " não encontrado");
        }
        contatoRepository.deleteById(id);
    }

}

