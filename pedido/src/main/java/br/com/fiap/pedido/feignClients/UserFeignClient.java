package br.com.fiap.pedido.feignClients;


import br.com.fiap.pedido.model.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "usuario")
public interface UserFeignClient {

    @GetMapping("/search")
    ResponseEntity<UserDTO> findByEmail(@RequestParam String email);
}
