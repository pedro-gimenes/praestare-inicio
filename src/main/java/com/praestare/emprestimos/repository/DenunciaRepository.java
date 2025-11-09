package com.praestare.emprestimos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.praestare.emprestimos.model.Denuncia;
import com.praestare.emprestimos.model.Usuario;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, Long>{

    public List<Denuncia> findByUsuarioId(Usuario usuario);
}
