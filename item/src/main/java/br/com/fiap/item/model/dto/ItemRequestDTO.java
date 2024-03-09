package br.com.fiap.item.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDTO {
    private String nome;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private Boolean ativo;
}
