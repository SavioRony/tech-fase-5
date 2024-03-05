package br.com.fiap.pedido.model.dto;

import br.com.fiap.pedido.model.ItemPedidoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;
    private List<ItemPedidoDTO> items;
    private String formaPagamento;
}
