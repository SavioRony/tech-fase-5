package br.com.fiap.pedido.mapper;

import br.com.fiap.pedido.model.ItemPedidoModel;
import br.com.fiap.pedido.model.dto.ItemPedidoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedidoModel toModel(ItemPedidoDTO dto);

    ItemPedidoDTO toDTO(ItemPedidoModel model);
}
