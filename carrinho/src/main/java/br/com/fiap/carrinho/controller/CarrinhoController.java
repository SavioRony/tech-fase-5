package br.com.fiap.carrinho.controller;

import br.com.fiap.carrinho.model.dto.CarrinhoDTO;
import br.com.fiap.carrinho.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarrinhoController {

    @Autowired
    protected CarrinhoService service;

    @GetMapping("")
    ResponseEntity<CarrinhoDTO> findByUser(@RequestHeader("X-User-Email") String email){

        var response = service.findCarrinhoByUser(email);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.noContent().build();
    }

    @PostMapping
    ResponseEntity<CarrinhoDTO> create(@RequestBody CarrinhoDTO model, @RequestHeader("X-User-Email") String email){
        return ResponseEntity.ok(service.create(model, email));
    }


    @DeleteMapping("")
    ResponseEntity<?> delete(@RequestHeader("X-User-Email") String email){
        var response = service.delete(email);

        return response != null ? ResponseEntity.status(200).build() : ResponseEntity.notFound().build();
    }
}
