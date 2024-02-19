package br.com.fiap.pedido.service.serviceImpl;

import br.com.fiap.pedido.model.PedidoModel;
import br.com.fiap.pedido.repository.PedidoRepository;
import br.com.fiap.pedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Override
    public PedidoModel create(PedidoModel model) {
        return repository.save(model);
    }

    @Override
    public PedidoModel update(PedidoModel model, Long idPedido) {
        var pedido = this.findById(idPedido);

        if(pedido != null){
            return repository.save(model);
        }
        return null;
    }

    @Override
    public PedidoModel findById(Long idPedido) {
        return repository.findById(idPedido).orElse(null);
    }

    @Override
    public List<PedidoModel> findAll() {
        return repository.findAll();
    }

    @Override
    public Long delete(Long id) {

        var pedido = this.findById(id);

        if(pedido != null){
            repository.deleteById(id);
            return id;
        }
        return null;
    }
}
