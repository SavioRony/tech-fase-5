package br.com.fiap.item.service.serviceImpl;

import br.com.fiap.item.model.ItemModel;
import br.com.fiap.item.repository.ItemRepository;
import br.com.fiap.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository repository;

    @Override
    public ItemModel create(ItemModel model) {
        return repository.save(model);
    }

    @Override
    public ItemModel update(ItemModel model, Long id) {
        var itemUpdated = findById(id);
        if(itemUpdated != null){
            return repository.save(model);
        }
        return null;
    }

    @Override
    public ItemModel updateEstoque(ItemModel model, Long id) {
        var itemUpdated = findById(id);
        if(itemUpdated != null){
            itemUpdated.setQuantidade(model.getQuantidade());
            return repository.save(itemUpdated);
        }
        return null;
    }

    @Override
    public ItemModel findById(Long id) {
        return generateStatus(repository.findById(id).orElse(null));
    }

    @Override
    public List<ItemModel> findAll() {
        var allItems = repository.findAll();
        if(!allItems.isEmpty()){
            allItems.forEach(this::generateStatus);
            return allItems;
        }
        return allItems;
    }

    @Override
    public Long delete(Long id) {
        var itemUpdated = findById(id);
        if(itemUpdated != null){
            repository.deleteById(id);
            return id;
        }
        return null;
    }

    private ItemModel generateStatus(ItemModel model){

        if(model != null){
            if(model.getQuantidade() > 0){
                model.setAtivo(Boolean.TRUE);
            }else {
                model.setAtivo(Boolean.FALSE);
            }
            return model;
        }
        return null;
    }
}
