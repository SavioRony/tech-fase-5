package br.com.fiap.carrinho.service.implementation;

import br.com.fiap.carrinho.exception.BadRequestException;
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
        CarrinhoDTO car = findCarrinhoByUser(email);
        validatesItemsContainingCart(car.getItems(), model.getItems());
        validatesNewItems(car.getItems(), model.getItems());
        validatesQuantityOfItems(car.getItems());
        CarrinhoModel carrinhoModel = veryfyUserGenerateNewBuy(mapper.toModel(car), email);
        return mapper.toDTO(repository.save(carrinhoModel));
    }

    @Override
    public CarrinhoDTO removeItems(ItemCarrinho dto, String email) {
        var car = findCarrinhoByUser(email);
        if (car != null) {
            var match = car.getItems().stream().filter(i -> (i.getId() != null && i.getId().equals(dto.getId())) ||
                    (i.getIdItem() != null && i.getIdItem().equals(dto.getIdItem()))).collect(Collectors.toList());

            if (!match.isEmpty()) {
                match.get(0).setQuantidade(dto.getQuantidade() > match.get(0).getQuantidade() ? 0
                        : (match.get(0).getQuantidade() - dto.getQuantidade()));
            }

            List<ItemCarrinho> itensVazio = car.getItems().stream().filter(c -> c.getQuantidade() == 0).collect(Collectors.toList());
            car.getItems().removeAll(itensVazio);
            CarrinhoModel carrinhoModel = veryfyUserGenerateNewBuy(mapper.toModel(car), email);
            return mapper.toDTO(repository.save(carrinhoModel));
        }
        throw new BadRequestException("your shopping cart was not found");
    }


    @Override
    public CarrinhoDTO findCarrinhoByUser(String email) {
        var user = userClient.findByEmail(email).getBody();
        CarrinhoDTO carrinhoDTO = new CarrinhoDTO();
        carrinhoDTO.setItems(new ArrayList<>());
        if (user != null) {
            var response = findByIdUsuario(user.getId());
            return response != null ? mapper.toDTO(response) : carrinhoDTO;
        }
        return carrinhoDTO;
    }

    @Override
    public CarrinhoModel findByIdUsuario(Long idUsuario) {
        return repository.findByIdUsuario(idUsuario).orElse(null);
    }

    @Override
    public Long delete(String email) {
        var response = this.findCarrinhoByUser(email);
        if (response != null) {
            repository.deleteById(response.getId());
            return response.getId();
        }
        return null;
    }

    @Override
    public List<CarrinhoModel> findAll() {
        return repository.findAll();
    }

    private void validatesItemsContainingCart(List<ItemCarrinho> itensCarrinho, List<ItemCarrinho> itensModel) {
        if (itensCarrinho.isEmpty()) {
            itensCarrinho.addAll(itensModel);
        }else {
            for (ItemCarrinho itemModel : itensModel) {
                for (ItemCarrinho itemCarrinho : itensCarrinho) {
                    if (itemCarrinho.getIdItem().equals(itemModel.getIdItem())) {
                        itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + itemModel.getQuantidade());
                    }
                }
            }
        }
    }

    private void validatesNewItems(List<ItemCarrinho> itensCarrinho, List<ItemCarrinho> itensModel) {
        if (itensCarrinho != null && !itensCarrinho.isEmpty() && itensModel != null && !itensModel.isEmpty()) {
            List<ItemCarrinho> itensNovos = itensModel.stream().filter(itemModel -> itensCarrinho.stream()
                            .noneMatch(itemCarrinho -> itemModel.getIdItem().equals(itemCarrinho.getIdItem())))
                    .collect(Collectors.toList());
            itensCarrinho.addAll(itensNovos);
        }
    }

    private void validatesQuantityOfItems(List<ItemCarrinho> itensCarrinho){
        if(itensCarrinho != null && !itensCarrinho.isEmpty()){
            for(ItemCarrinho item : itensCarrinho){
                var itemResponse = itemClient.findById(item.getIdItem()).getBody();
                if(itemResponse != null){
                    if(item.getQuantidade() > itemResponse.getQuantidade()){
                        throw new BadRequestException("Quantidade invalida !");
                    }
                }else{
                    throw new BadRequestException("Item not found");
                }
            }
        }
    }
    private CarrinhoModel veryfyUserGenerateNewBuy(CarrinhoModel model, String email) {
        var user = userClient.findByEmail(email).getBody();
        if (user != null) {
            var car = findByIdUsuario(user.getId());
            model.setIdUsuario(user.getId());
            if (car != null) {
                model.setId(car.getId());
            }
            return model;
        }
        throw new BadRequestException("User not found");
    }
}