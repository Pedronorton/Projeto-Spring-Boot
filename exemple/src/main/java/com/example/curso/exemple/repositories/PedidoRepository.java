package com.example.curso.exemple.repositories;

import com.example.curso.exemple.domain.Cliente;
import com.example.curso.exemple.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Transactional(readOnly = true)
    List<Pedido> findByCliente(Cliente cli);
}
