package br.com.fiap.pedido.service;

import br.com.fiap.pedido.model.PedidoModel;
import br.com.fiap.pedido.model.dto.PedidoDTO;

import java.util.List;

public interface PedidoService {

    PedidoDTO create(PedidoDTO model, String email);

    PedidoDTO findByUser(String email);

    PedidoModel findByIdUsuario(Long idUsuario);

    List<PedidoDTO> findAll(String email);

    Long delete(String email);
}
