package br.com.fiap.item.controller;

import br.com.fiap.item.mapper.ItemMapper;
import br.com.fiap.item.model.ItemModel;
import br.com.fiap.item.model.dto.ItemRequestDTO;
import br.com.fiap.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService service;
    @Autowired
    private ItemMapper mapper;

    @GetMapping("/all")
    ResponseEntity<List<ItemModel>> findAll (){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    ResponseEntity<ItemModel> create (@RequestBody ItemRequestDTO requestDTO, @RequestHeader("X-User-Email") String email){
        return ResponseEntity.ok(service.create(mapper.toModel(requestDTO)));
    }

    @GetMapping("/{id}")
    ResponseEntity<ItemModel> findById (@PathVariable(name = "id")Long id){

        var response = service.findById(id);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<ItemModel> update (@PathVariable(name = "id")Long id, @RequestBody ItemRequestDTO requestDTO){

        var response = service.update(mapper.toModel(requestDTO),id);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }
}
