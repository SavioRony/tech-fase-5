package br.com.fiap.pedido.service;

import br.com.fiap.pedido.model.PedidoModel;

import java.util.List;

public interface PedidoService {

    PedidoModel create(PedidoModel model, String email);

    PedidoModel findByUser(String email);

    PedidoModel findByIdUsuario(Long idUsuario);

    List<PedidoModel> findAll(String email);

    Long delete(String email);
}
