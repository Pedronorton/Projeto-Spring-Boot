package com.example.curso.exemple.repositories;

import com.example.curso.exemple.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface EstadoRepository extends JpaRepository<Estado, Integer> {
}
