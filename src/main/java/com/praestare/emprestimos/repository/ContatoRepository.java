package com.praestare.emprestimos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.praestare.emprestimos.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
    
}
