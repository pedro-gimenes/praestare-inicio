package com.praestare.emprestimos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.praestare.emprestimos.model.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {
    
}
