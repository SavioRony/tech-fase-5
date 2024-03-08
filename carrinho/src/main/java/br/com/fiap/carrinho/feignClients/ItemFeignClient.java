package br.com.fiap.carrinho.feignClients;

import br.com.fiap.carrinho.model.dto.ItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "/item")
public interface ItemFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<ItemDTO> findById(@RequestParam(name = "id") Long id);
}
