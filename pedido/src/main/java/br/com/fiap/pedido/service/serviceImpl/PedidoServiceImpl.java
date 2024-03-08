package br.com.fiap.pedido.service.serviceImpl;

import br.com.fiap.pedido.feignClients.CarrinhoFeignClient;
import br.com.fiap.pedido.feignClients.ItemFeignClient;
import br.com.fiap.pedido.feignClients.UserFeignClient;
import br.com.fiap.pedido.mapper.PedidoMapper;
import br.com.fiap.pedido.model.ItemPedidoModel;
import br.com.fiap.pedido.model.PedidoModel;
import br.com.fiap.pedido.model.dto.*;
import br.com.fiap.pedido.repository.PedidoRepository;
import br.com.fiap.pedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    protected PedidoRepository repository;

    @Autowired
    protected UserFeignClient userClient;

    @Autowired
    protected CarrinhoFeignClient carrinhoClient;

    @Autowired
    protected ItemFeignClient itemClient;

    @Autowired
    protected PedidoMapper mapper;


    private final List<ItemDTO> updateListEstoque = new ArrayList<>();

    @Override
    public PedidoDTO create(PedidoDTO model, String email) {
        var user = userClient.findByEmail(email).getBody();
        if(user != null){
            var buy = carrinhoClient.findByUser(user.getEmail()).getBody();
            if(buy != null){

                var modelToSave = generateModel(mapper.toModel(model),user);

                if(modelToSave.getItems() != null){
                    modelToSave.getItems().forEach(this::updateQuantytyAndSet);
                }

                var response = repository.save(modelToSave);
                updateListEstoque.forEach(x -> itemClient.updateEstoque(x.getId(),x));
                updateListEstoque.clear();
                carrinhoClient.delete(user.getEmail());
                return mapper.toDTO(response);
            }

            return new PedidoDTO();
        }

        throw new IllegalArgumentException("User not found");
    }

    private PedidoModel generateModel(PedidoModel model, UserDTO user){
        model.setDataPedido(LocalDate.now());
        model.setIdUsuario(user.getId());
        return model;
    }

    private void updateQuantytyAndSet(ItemPedidoModel dto){

        var itemResponse = itemClient.findById(dto.getIdItem()).getBody();
        if(itemResponse != null){
            if(dto.getQuantidade() > itemResponse.getQuantidade()){
                throw new IllegalArgumentException("Quantidade invalida !");
            }

            dto.setSubTotal(BigDecimal.valueOf(dto.getQuantidade()).multiply(itemResponse.getValorUnitario()));
            itemResponse.setQuantidade(itemResponse.getQuantidade() - dto.getQuantidade());
            updateListEstoque.add(itemResponse);

        }else{
            throw new IllegalArgumentException("Item not found");
        }
    }


    @Override
    public PedidoDTO findByUser(String email) {

        var user = userClient.findByEmail(email).getBody();
        if(user != null){
            var response = findByIdUsuario(user.getId());
            return response != null ? mapper.toDTO(response) : null;
        }
        return null;
    }

    @Override
    public PedidoModel findByIdUsuario(Long idUsuario) {
        return repository.findByIdUsuario(idUsuario).orElse(null);
    }

    @Override
    public List<PedidoDTO> findAll(String email) {
        var response = repository.findAll();
        var user = userClient.findByEmail(email).getBody();

        if(user != null && !response.isEmpty()){
            return mapper.toResponseAllDto(response.stream().filter(filter -> filter.getIdUsuario().equals(user.getId())).collect(Collectors.toList()));
        }
        return new ArrayList<>();
    }

    @Override
    public Long delete (String email) {

        var response = this.findByUser(email);

        if(response != null){
            repository.deleteById(response.getId());
            return response.getId();
        }
        return null;
    }
}
