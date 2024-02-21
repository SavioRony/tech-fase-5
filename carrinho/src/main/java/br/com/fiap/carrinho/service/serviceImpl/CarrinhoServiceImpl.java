package br.com.fiap.carrinho.service.serviceImpl;

import br.com.fiap.carrinho.model.CarrinhoModel;
import br.com.fiap.carrinho.repository.CarrinhoRepository;
import br.com.fiap.carrinho.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

    @Autowired
    protected CarrinhoRepository repository;

    @Override
    public CarrinhoModel create(CarrinhoModel model) {
        return repository.save(model);
    }

    @Override
    public CarrinhoModel update(CarrinhoModel model, Long id) {
        var response = this.findById(id);

        if(response != null){
            return repository.save(model);
        }
        return null;
    }

    @Override
    public CarrinhoModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Long delete(Long id) {

        var response = this.findById(id);

        if(response != null){
             repository.deleteById(id);
             return id;
        }
        return null;
    }

    @Override
    public List<CarrinhoModel> findAll() {
        return repository.findAll();
    }
}
