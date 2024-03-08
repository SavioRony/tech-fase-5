package br.com.fiap.pedido.mapper;

import br.com.fiap.pedido.model.PedidoModel;
import br.com.fiap.pedido.model.dto.PedidoDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    PedidoModel toModel(PedidoDTO dto);

    PedidoDTO toDTO(PedidoModel model);

    List<PedidoDTO> toResponseAllDto(List<PedidoModel> all);
}
