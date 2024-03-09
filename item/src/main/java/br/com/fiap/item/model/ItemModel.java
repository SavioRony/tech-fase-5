package br.com.fiap.item.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Data
@Entity
@Table(name = "tb_item")
public class ItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private Boolean ativo;

    public void update(ItemModel item){
        this.id = id == null ? item.getId() : this.id;
        this.nome = nome == null ? item.getNome() : this.nome;
        this.quantidade = quantidade == null ? item.getQuantidade() : this.quantidade;
        this.valorUnitario = valorUnitario == null ? item.getValorUnitario() : this.valorUnitario;
        this.ativo = ativo == null ? item.getAtivo() : this.ativo;
    }
}
