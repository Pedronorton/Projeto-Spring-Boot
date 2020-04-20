package com.example.curso.exemple.repositories;

import com.example.curso.exemple.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
}
