package br.com.fiap.carrinho.controller;

import br.com.fiap.carrinho.model.CarrinhoModel;
import br.com.fiap.carrinho.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/carrinho")
public class CarrinhoController {

    @Autowired
    protected CarrinhoService service;

    @GetMapping("/id")
    ResponseEntity<CarrinhoModel> findById(@PathVariable(name = "id") Long id){

        var response = service.findById(id);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @PostMapping
    ResponseEntity<CarrinhoModel> create(@RequestBody CarrinhoModel model){
        return ResponseEntity.ok(service.create(model));
    }

    @GetMapping
    ResponseEntity<List<CarrinhoModel>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/id")
    ResponseEntity<CarrinhoModel> update(@RequestBody CarrinhoModel model, @PathVariable(name = "id") Long id){

        var response = service.update(model, id);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/id")
    ResponseEntity<?> delete(@PathVariable(name = "id") Long id){
        var response = service.delete(id);

        return response != null ? ResponseEntity.status(200).build() : ResponseEntity.notFound().build();
    }
}
