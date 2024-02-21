package br.com.fiap.item.service;

import br.com.fiap.item.model.ItemModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ItemService {

    ItemModel create(ItemModel model);

    ItemModel update(ItemModel model, Long id);

    ItemModel findById(Long id);

    List<ItemModel> findAll();

    Long delete(Long id);
}
