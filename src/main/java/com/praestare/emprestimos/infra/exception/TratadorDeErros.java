package com.praestare.emprestimos.infra.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        var lista = erros.stream()
            .map(DadosErroValidacao::new)
            .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(lista);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<String> tratarErroRegraDeNegocio(ValidacaoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    public static record DadosErroValidacao(String campo, String mensagem) {
        
        public DadosErroValidacao(FieldError error){
            this(error.getField(), error.getDefaultMessage());}
    }

}
