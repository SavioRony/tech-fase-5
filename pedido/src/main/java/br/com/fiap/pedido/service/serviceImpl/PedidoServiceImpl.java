package br.com.fiap.pedido.service.serviceImpl;

import br.com.fiap.pedido.feignClients.CarrinhoFeignClient;
import br.com.fiap.pedido.feignClients.ItemFeignClient;
import br.com.fiap.pedido.feignClients.UserFeignClient;
import br.com.fiap.pedido.model.ItemPedidoModel;
import br.com.fiap.pedido.model.PedidoModel;
import br.com.fiap.pedido.model.dto.CarrinhoDTO;
import br.com.fiap.pedido.model.dto.ItemCarrinnhoDTO;
import br.com.fiap.pedido.model.dto.ItemDTO;
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
    private PedidoRepository repository;

    @Autowired
    private UserFeignClient userClient;

    @Autowired
    private CarrinhoFeignClient carrinhoClient;

    @Autowired
    private ItemFeignClient itemClient;


    private final List<ItemDTO> updateListEstoque = new ArrayList<>();

    @Override
    public PedidoModel create(PedidoModel model, String email) {
        var user = userClient.findByEmail(email).getBody();
        if(user != null){
            var buy = carrinhoClient.findByUser(user.getEmail()).getBody();
            if(buy != null){

                var response = repository.save(generateModel(model,buy));
                updateListEstoque.forEach(x -> itemClient.updateEstoque(x.getId(),x));
                updateListEstoque.clear();
                carrinhoClient.delete(user.getEmail());
                return response;
            }

            return new PedidoModel();
        }

        throw new IllegalArgumentException("User not found");
    }

    private PedidoModel generateModel(PedidoModel dto,CarrinhoDTO carrinho){
        PedidoModel model = new PedidoModel();
        model.setDataPedido(LocalDate.now());
        model.setFormaPagamento(dto.getFormaPagamento());
        model.setItems(generateItems(carrinho.getItems()));
        model.setDataPedido(LocalDate.now());
        model.setFormaPagamento(dto.getFormaPagamento());
        return model;
    }

    private List<ItemPedidoModel> generateItems(List<ItemCarrinnhoDTO> list){
        List<ItemPedidoModel> mapedList = new ArrayList<>();
        for(ItemCarrinnhoDTO carItem : list){
            mapedList.add(updateQuantytyAndSet(carItem));
        }

        return mapedList;
    }

    private ItemPedidoModel updateQuantytyAndSet(ItemCarrinnhoDTO dto){

        var itemResponse = itemClient.findById(dto.getIdItem()).getBody();
        if(itemResponse != null){
            if(dto.getQuantidade() > itemResponse.getQuantidade()){
                throw new IllegalArgumentException("Quantidade invalida !");
            }

            var item = new ItemPedidoModel();
            item.setIdItem(dto.getIdItem());
            item.setQuantidade(dto.getQuantidade());
            item.setSubTotal(BigDecimal.valueOf(dto.getQuantidade()).multiply(itemResponse.getValorUnitario()));

            itemResponse.setQuantidade(itemResponse.getQuantidade() - dto.getQuantidade());
            updateListEstoque.add(itemResponse);
            return item;

        }else{
            throw new IllegalArgumentException("Item not found");
        }
    }


    @Override
    public PedidoModel findByUser(String email) {

        var user = userClient.findByEmail(email).getBody();
        if(user != null){
            return findByIdUsuario(user.getId());
        }
        return null;
    }

    @Override
    public PedidoModel findByIdUsuario(Long idUsuario) {
        return repository.findByIdUsuario(idUsuario).orElse(null);
    }

    @Override
    public List<PedidoModel> findAll(String email) {
        var response = repository.findAll();
        var user = userClient.findByEmail(email).getBody();

        if(user != null && !response.isEmpty()){
            return response.stream().filter(filter -> filter.getIdUsuario().equals(user.getId())).collect(Collectors.toList());
        }
        return response;
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
