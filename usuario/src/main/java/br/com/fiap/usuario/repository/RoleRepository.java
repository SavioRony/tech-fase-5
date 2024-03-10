package br.com.fiap.usuario.repository;

import br.com.fiap.usuario.model.Role;
import br.com.fiap.usuario.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
