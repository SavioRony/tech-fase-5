package br.com.fiap.carrinho.repository;

import br.com.fiap.carrinho.model.CarrinhoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<CarrinhoModel, Long> {

    Optional<CarrinhoModel> findByIdUsuario(Long id);
}
