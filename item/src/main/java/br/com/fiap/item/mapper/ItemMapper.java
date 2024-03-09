package br.com.fiap.item.mapper;


import br.com.fiap.item.model.ItemModel;
import br.com.fiap.item.model.dto.ItemRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemRequestDTO toDTO(ItemModel model);

    ItemModel toModel(ItemRequestDTO dto);
}
