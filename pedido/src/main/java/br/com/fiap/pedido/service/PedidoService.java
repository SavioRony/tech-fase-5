package br.com.fiap.pedido.service;

import br.com.fiap.pedido.model.dto.PedidoDTO;
import br.com.fiap.pedido.model.dto.PedidoRequestDTO;

import java.util.List;

public interface PedidoService {

    PedidoDTO create(PedidoRequestDTO model, String email);

    List<PedidoDTO> findAll(String email);
}
