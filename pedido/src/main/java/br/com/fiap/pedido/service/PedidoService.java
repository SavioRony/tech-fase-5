package br.com.fiap.pedido.service;

import br.com.fiap.pedido.model.PedidoModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PedidoService {

    PedidoModel create(PedidoModel model);

    PedidoModel update(PedidoModel model, Long idPedido);

    PedidoModel findById(Long idPedido);

    List<PedidoModel> findAll();

    Long delete(Long id);
}
