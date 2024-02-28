package br.com.fiap.pedido.repository;

import br.com.fiap.pedido.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    Optional<PedidoModel> findByIdUsuario(Long id);
}
