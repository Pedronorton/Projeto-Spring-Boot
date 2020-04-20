package com.example.curso.exemple.repositories;

import com.example.curso.exemple.domain.Categoria;
import com.example.curso.exemple.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> { //essa interface ela é capaz de fazer busca com base em um tipo de dado e seu tipo identificador <tipo de dado, identificador>

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias ")//Para eu fazer uma consulta qualquer usando JPQL
    Page<Produto> search(@Param("nome") String nome,@Param("categorias") List<Categoria> categorias, Pageable pageRequest);

}
