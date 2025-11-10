package com.praestare.emprestimos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.praestare.emprestimos.model.Contato;
import com.praestare.emprestimos.model.Usuario;



@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    Page<Contato> findByUsuario(Usuario usuario, Pageable pageable);
    
}
