package br.com.fiap.carrinho.service;

import br.com.fiap.carrinho.model.CarrinhoModel;
import br.com.fiap.carrinho.model.ItemCarrinho;
import br.com.fiap.carrinho.model.dto.CarrinhoDTO;

import java.util.List;

public interface CarrinhoService {

    CarrinhoDTO addItems(CarrinhoDTO model, String email);

    CarrinhoDTO removeItems(ItemCarrinho model, String email);

    CarrinhoDTO findCarrinhoByUser(String email);

    CarrinhoModel findByIdUsuario(Long idUsuario);

    Long delete(String email);

    List<CarrinhoModel> findAll();
}
