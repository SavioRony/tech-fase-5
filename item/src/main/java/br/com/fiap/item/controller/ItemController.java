package br.com.fiap.item.controller;

import br.com.fiap.item.model.ItemModel;
import br.com.fiap.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService service;

    @GetMapping("/all")
    ResponseEntity<List<ItemModel>> findAll (){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    ResponseEntity<ItemModel> create (@RequestBody ItemModel model){
        return ResponseEntity.ok(service.create(model));
    }

    @GetMapping("/{id}")
    ResponseEntity<ItemModel> findById (@PathVariable(name = "id")Long id){

        var response = service.findById(id);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<ItemModel> update (@PathVariable(name = "id")Long id, @RequestBody ItemModel model){

        var response = service.update(model,id);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete (@PathVariable(name = "id")Long id){
        var response = service.delete(id);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }
}
