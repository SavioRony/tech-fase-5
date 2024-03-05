package br.com.fiap.carrinho.mapper;

import br.com.fiap.carrinho.model.CarrinhoModel;
import br.com.fiap.carrinho.model.dto.CarrinhoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarrinhoMapper {

    CarrinhoDTO toDTO(CarrinhoModel model);

    CarrinhoModel toModel(CarrinhoDTO dto);
}
