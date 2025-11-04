package com.praestare.emprestimos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.praestare.emprestimos.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long>{

        UserDetails findByLogin(String login);
}
