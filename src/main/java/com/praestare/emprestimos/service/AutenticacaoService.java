package com.praestare.emprestimos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praestare.emprestimos.repository.UsuarioRepository;



@Service
public class AutenticacaoService implements UserDetailService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(Long id) throws UsernameNotFoundException {
        return usuarioRepository.findById(id);
    }
}