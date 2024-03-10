package br.com.fiap.usuario.model;

import br.com.fiap.usuario.repository.RoleRepository;
import br.com.fiap.usuario.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleClient = new Role("ROLE_CLIENT");
        roleRepository.save(roleAdmin);
        roleRepository.save(roleClient);
        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("$2a$10$NYFZ/8WaQ3Qb6FCs.00jce4nxX9w7AkgWVsQCG6oUwTAcZqP9Flqu");
        admin.getRoles().add(new Role(1L, "ROLE_ADMIN"));
        userRepository.save(admin);
    }
}