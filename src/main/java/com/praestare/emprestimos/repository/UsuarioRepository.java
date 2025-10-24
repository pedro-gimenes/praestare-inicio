package com.praestare.emprestimos.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.praestare.emprestimos.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    
}
