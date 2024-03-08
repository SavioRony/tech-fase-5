package br.com.fiap.carrinho.service.implementation;

import br.com.fiap.carrinho.feignClients.ItemFeignClient;
import br.com.fiap.carrinho.feignClients.UserFeignClient;
import br.com.fiap.carrinho.mapper.CarrinhoMapper;
import br.com.fiap.carrinho.model.CarrinhoModel;
import br.com.fiap.carrinho.model.ItemCarrinho;
import br.com.fiap.carrinho.model.dto.CarrinhoDTO;
import br.com.fiap.carrinho.repository.CarrinhoRepository;
import br.com.fiap.carrinho.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

    @Autowired
    protected CarrinhoRepository repository;

    @Autowired
    protected ItemFeignClient itemClient;

    @Autowired
    protected UserFeignClient userClient;

    @Autowired
    protected CarrinhoMapper mapper;

    @Override
    public CarrinhoDTO addItems(CarrinhoDTO dto, String email) {
        var model = mapper.toModel(dto);
        validateQuantytyItems(model);
        return mapper.toDTO(repository.save(veryfyUserGenerateNewBuy(model, email)));
    }

    @Override
    public CarrinhoDTO removeItems(ItemCarrinho dto, String email) {
        var car = findCarrinhoByUser(email);
        if (car != null) {
            var match = car.getItems().stream().filter(i -> (i.getId() != null && i.getId().equals(dto.getId())) ||
                    ( i.getIdItem() != null && i.getIdItem().equals(dto.getIdItem()))).collect(Collectors.toList());

            if (!match.isEmpty()) {
                match.get(0).setQuantidade(dto.getQuantidade() > match.get(0).getQuantidade() ? 0 : (match.get(0).getQuantidade() - dto.getQuantidade()));
            }

            return mapper.toDTO(repository.save(mapper.toModel(car)));
        }
        throw new IllegalArgumentException("your shopping cart was not found");
    }

    private CarrinhoModel veryfyUserGenerateNewBuy(CarrinhoModel model, String email) {

        var user = userClient.findByEmail(email).getBody();
        if(user != null){
            var car = findByIdUsuario(user.getId());
            model.setIdUsuario(user.getId());
            if(car != null){
                model.setId(car.getId());
                validateAndUpdateItemsExistsInCar(model,car);
            }
            return model;
        }
        throw new IllegalArgumentException("User not found");
    }

    private void validateAndUpdateItemsExistsInCar(CarrinhoModel model, CarrinhoModel car){

        if((model.getItems() != null && !model.getItems().isEmpty()) && (car.getItems() != null && !car.getItems().isEmpty())){
            List<ItemCarrinho> updateListItems = new ArrayList<>();
            for(ItemCarrinho item : car.getItems()){
                var match = model.getItems().stream().filter(i -> i.getIdItem().equals(item.getIdItem())).collect(Collectors.toList());
                if(!match.isEmpty()){
                    item.setQuantidade(item.getQuantidade() + match.get(0).getQuantidade());
                    updateListItems.add(item);
                }else {
                    updateListItems.add(item);
                }
            }

            model.getItems().clear();
            model.getItems().addAll(updateListItems);
            model.getItems().addAll(model.getItems().stream().filter(i -> !updateListItems.contains(i)).collect(Collectors.toList()));
            validateQuantytyItems(model);
        }
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
    public CarrinhoDTO findCarrinhoByUser(String email) {
        var user = userClient.findByEmail(email).getBody();
        if(user != null){
            var response = findByIdUsuario(user.getId());
            return response != null ? mapper.toDTO(response) : null;
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