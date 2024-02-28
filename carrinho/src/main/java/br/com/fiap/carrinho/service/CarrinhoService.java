package br.com.fiap.carrinho.service;

import br.com.fiap.carrinho.model.CarrinhoModel;

import java.util.List;

public interface CarrinhoService {

    CarrinhoModel create(CarrinhoModel model, String email);

    CarrinhoModel findCarrinhoByUser(String email);

    CarrinhoModel findByIdUsuario(Long idUsuario);

    Long delete(String email);

    List<CarrinhoModel> findAll();
}
