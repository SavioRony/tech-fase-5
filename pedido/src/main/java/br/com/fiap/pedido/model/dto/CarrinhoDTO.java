package br.com.fiap.pedido.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarrinhoDTO {

    List<ItemCarrinnhoDTO> items;
}
