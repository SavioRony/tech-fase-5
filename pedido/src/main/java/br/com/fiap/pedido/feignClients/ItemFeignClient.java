package br.com.fiap.pedido.feignClients;

import br.com.fiap.pedido.model.dto.ItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "item")
public interface ItemFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<ItemDTO> findById(@RequestParam(name = "id") Long id);

    @PutMapping("/estoque/{id}")
    ResponseEntity<?> updateEstoque(@PathVariable(name = "id") Long id, @RequestBody ItemDTO model);
}
