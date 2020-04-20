package com.example.curso.exemple.repositories;

import com.example.curso.exemple.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> { //essa interface ela é capaz de fazer busca com base em um tipo de dado e seu tipo identificador <tipo de dado, identificador>

}
