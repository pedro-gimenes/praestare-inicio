package com.praestare.emprestimos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praestare.emprestimos.controller.ContatoMapper;
import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;
import com.praestare.emprestimos.model.dto.ContatoDto;
import com.praestare.emprestimos.repository.ContatoRepository;
import com.praestare.emprestimos.repository.UsuarioRepository;

@Service
public class ContatoService {
    
    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Contato criarContato(ContatoDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        Contato contato = ContatoMapper.toEntity(dto, usuario);
        return contatoRepository.save(contato);
    }

    public List<Contato> atualizarContatos(List<ContatoDto> contatoDtos, Usuario usuario) {
        // carregar contatos atuais do usuário
        List<Contato> atuais = Optional.ofNullable(usuario.getContatos()).orElseGet(ArrayList::new);

        // map de id -> Contato atual para lookup rápido
        Map<Long, Contato> atuaisById = atuais.stream()
            .filter(c -> c.getId() != null)
            .collect(Collectors.toMap(Contato::getId, c -> c));

        List<Contato> result = new ArrayList<>();

        for (ContatoDto dto : Optional.ofNullable(contatoDtos).orElseGet(ArrayList::new)) {
            if (dto.getId() != null && atuaisById.containsKey(dto.getId())) {
                // atualizar existente
                Contato existente = atuaisById.get(dto.getId());
                ContatoMapper.updateEntityFromDto(existente, dto, usuario);
                result.add(existente);
                atuaisById.remove(dto.getId());
            } else {
                // novo contato
                Contato novo = ContatoMapper.toEntity(dto, usuario);
                result.add(novo);
            }
        }

        // contatos remanescentes em atuaisById foram removidos pelo cliente -> deletar
        if (!atuaisById.isEmpty()) {
            contatoRepository.deleteAll(atuaisById.values());
        }

        // salvar todos (novos e atualizados). saveAll persiste alterações e atribui ids para novos.
        List<Contato> salvos = contatoRepository.saveAll(result);

        // atualizar associação no usuário (opcional, para manter entidade sincronizada)
        usuario.setContatos(new ArrayList<>(salvos));

        return salvos;
    }

    // método de conveniência para atualizar um contato individual (usado pelo controller PUT)
    public Contato atualizarContato(Long id, ContatoDto dto) {
        Contato contato = contatoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        ContatoMapper.(contato, dto, usuario);
        return contatoRepository.save(contato);
    }

}

