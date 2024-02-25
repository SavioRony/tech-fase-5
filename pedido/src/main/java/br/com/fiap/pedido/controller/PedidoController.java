package br.com.fiap.pedido.controller;

import br.com.fiap.pedido.model.PedidoModel;
import br.com.fiap.pedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    ResponseEntity<PedidoModel> create(@RequestBody PedidoModel model){
        return ResponseEntity.ok(service.create(model));
    }

    @PutMapping("/{id}")
    ResponseEntity<PedidoModel> update(@RequestBody PedidoModel model, @PathVariable(name = "id") Long id){
        var response = service.update(model, id);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping
    ResponseEntity<List<PedidoModel>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable(name = "id") Long id){

        var response = service.delete(id);

        return response != null ? ResponseEntity.status(200).build() : ResponseEntity.notFound().build();
    }
}
