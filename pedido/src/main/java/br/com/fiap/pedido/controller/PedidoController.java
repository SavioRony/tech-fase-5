package br.com.fiap.pedido.controller;

import br.com.fiap.pedido.model.dto.PedidoDTO;
import br.com.fiap.pedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PedidoController {

    @Autowired
    protected PedidoService service;

    @PostMapping
    ResponseEntity<PedidoDTO> create(@RequestBody PedidoDTO model, @RequestHeader("X-User-Email") String email){
        return ResponseEntity.ok(service.create(model, email));
    }

    @GetMapping("/all")
    ResponseEntity<List<PedidoDTO>> findAll(@RequestHeader("X-User-Email") String email){
        return ResponseEntity.ok(service.findAll(email));
    }

    @GetMapping
    ResponseEntity<PedidoDTO> findByUser(@RequestHeader("X-User-Email") String email){

        var response = service.findByUser(email);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.noContent().build();
    }

    @DeleteMapping("")
    ResponseEntity<?> delete(@RequestHeader("X-User-Email") String email){

        var response = service.delete(email);

        return response != null ? ResponseEntity.status(200).build() : ResponseEntity.notFound().build();
    }
}
