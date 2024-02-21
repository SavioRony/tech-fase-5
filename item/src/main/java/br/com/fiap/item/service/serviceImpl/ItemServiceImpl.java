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
    public ItemModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ItemModel> findAll() {
        return repository.findAll();
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
}
