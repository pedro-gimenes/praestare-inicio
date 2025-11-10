package com.praestare.emprestimos.service;

import static com.praestare.emprestimos.mapper.ContatoMapper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Contato> atualizarContatos(List<ContatoDto> contatos, Usuario usuario) {

        List<Contato> atuais = Optional.ofNullable(usuario.getContatos()).orElseGet(ArrayList::new);

        Map<Long, Contato> atuaisById = atuais.stream()
            .filter(c -> c.getId() != null)
            .collect(Collectors.toMap(Contato::getId, c -> c));

        List<Contato> result = new ArrayList<>();

        for (ContatoDto dto : Optional.ofNullable(contatos).orElseGet(ArrayList::new)) {
            if (dto.getId() != null && atuaisById.containsKey(dto.getId())) {
                Contato existente = atuaisById.get(dto.getId());
                ContatoMapper.updateEntityFromDto(existente, dto, usuario);
                result.add(existente);
                atuaisById.remove(dto.getId());
            } else {
                Contato novo = ContatoMapper.toEntity(dto, usuario);
                result.add(novo);
            }
        }

        if (!atuaisById.isEmpty()) {
            contatoRepository.deleteAll(atuaisById.values());
        }

        List<Contato> salvos = contatoRepository.saveAll(result);
        usuario.setContatos(new ArrayList<>(salvos));

        return salvos;
    }

    public Contato atualizarContato(Long id, ContatoDto dto) {
        Contato contato = contatoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        updateEntityFromDto(contato, dto, usuario);
        return contatoRepository.save(contato);
    }

    public void deletarContato(Long id){
        if(!contatoRepository.existsById(id)){
            throw new EntityNotFoundException("Usuário com ID " + id + " não encontrado");
        }
        contatoRepository.deleteById(id);
    }

}

