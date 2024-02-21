package br.com.fiap.carrinho.service;

import br.com.fiap.carrinho.model.CarrinhoModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CarrinhoService {

    CarrinhoModel create(CarrinhoModel model);

    CarrinhoModel update(CarrinhoModel model, Long id);

    CarrinhoModel findById(Long id);

    Long delete(Long id);

    List<CarrinhoModel> findAll();
}
