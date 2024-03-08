package br.com.fiap.pedido.feignClients;

import br.com.fiap.pedido.model.dto.CarrinhoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "carrinho")
public interface CarrinhoFeignClient {

    @GetMapping
    ResponseEntity<CarrinhoDTO> findByUser(@RequestHeader("X-User-Email") String email);

    @DeleteMapping
    ResponseEntity<?> delete(@RequestHeader("X-User-Email") String email);
}
