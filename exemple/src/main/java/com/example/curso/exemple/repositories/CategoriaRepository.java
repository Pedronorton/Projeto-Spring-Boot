package com.example.curso.exemple.repositories;

import com.example.curso.exemple.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> { //essa interface ela é capaz de fazer busca com base em um tipo de dado e seu tipo identificador <tipo de dado, identificador>


}
