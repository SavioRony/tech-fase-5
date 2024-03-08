package br.com.fiap.pedido.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long id;
    private String nome;
    private Integer quantidade;
    private BigDecimal valorUnitario;
}
