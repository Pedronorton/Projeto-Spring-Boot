package com.example.curso.exemple.repositories;

import com.example.curso.exemple.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
