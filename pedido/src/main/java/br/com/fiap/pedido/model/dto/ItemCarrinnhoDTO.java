package br.com.fiap.pedido.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrinnhoDTO {

    private Integer quantidade;
    private Long idItem;
}
