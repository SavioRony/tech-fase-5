package br.com.fiap.carrinho.service.implementation;

import br.com.fiap.carrinho.feignClients.ItemFeignClient;
import br.com.fiap.carrinho.feignClients.UserFeignClient;
import br.com.fiap.carrinho.model.CarrinhoModel;
import br.com.fiap.carrinho.model.ItemCarrinho;
import br.com.fiap.carrinho.repository.CarrinhoRepository;
import br.com.fiap.carrinho.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

    @Autowired
    protected CarrinhoRepository repository;

    @Autowired
    protected ItemFeignClient itemClient;

    @Autowired
    protected UserFeignClient userClient;

    @Override
    public CarrinhoModel create(CarrinhoModel model, String email) {
        validateQuantytyItems(model);
        return repository.save(veryfyUserGenerateNewBuy(model, email));
    }

    private CarrinhoModel veryfyUserGenerateNewBuy(CarrinhoModel model, String email) {

        var user = userClient.findByEmail(email).getBody();
        if(user != null){
            var car = findByIdUsuario(user.getId());
            model.setIdUsuario(user.getId());
            if(car != null){
                model.setId(car.getId());
            }
            return model;
        }
        throw new IllegalArgumentException("User not found");
    }

    private void validateQuantytyItems(CarrinhoModel model){
        if(model.getItems() != null && !model.getItems().isEmpty()){
            for(ItemCarrinho item : model.getItems()){
                var itemResponse = itemClient.findById(item.getIdItem()).getBody();
                if(itemResponse != null){
                    if(item.getQuantidade() > itemResponse.getQuantidade()){
                        throw new IllegalArgumentException("Quantidade invalida !");
                    }
                }else{
                    throw new IllegalArgumentException("Item not found");
                }
            }
        }
    }

    @Override
    public CarrinhoModel findCarrinhoByUser(String email) {
       var user = userClient.findByEmail(email).getBody();
       if(user != null){
           return findByIdUsuario(user.getId());
       }
       return null;
    }

    @Override
    public CarrinhoModel findByIdUsuario(Long idUsuario) {
        return repository.findByIdUsuario(idUsuario).orElse(null);
    }

    @Override
    public Long delete(String email) {

        var response = this.findCarrinhoByUser(email);

        if(response != null){
             repository.deleteById(response.getId());
             return response.getId();
        }
        return null;
    }

    @Override
    public List<CarrinhoModel> findAll() {
        return repository.findAll();
    }
}
