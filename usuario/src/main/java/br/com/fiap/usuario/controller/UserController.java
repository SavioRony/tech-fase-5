package br.com.fiap.usuario.controller;

import br.com.fiap.usuario.model.User;
import br.com.fiap.usuario.model.dto.UserClientRequest;
import br.com.fiap.usuario.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;


    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<User> findByEmail(@RequestParam String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @PostMapping("/client/register")
    public ResponseEntity<User> findById(@RequestBody UserClientRequest requestClientToUser){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(requestClientToUser));
    }

}
