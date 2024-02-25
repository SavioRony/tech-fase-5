package br.com.fiap.usuario.service;

import br.com.fiap.usuario.model.User;
import br.com.fiap.usuario.model.dto.UserClientRequest;
import br.com.fiap.usuario.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findById(long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

    }

    public User findByEmail(String email){
        return repository.findByEmail(email).orElse(null);
    }

    public User saveUser(UserClientRequest request){
        User user = new User();
        user.requestClientToUser(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        return repository.save(user);
    }
}
