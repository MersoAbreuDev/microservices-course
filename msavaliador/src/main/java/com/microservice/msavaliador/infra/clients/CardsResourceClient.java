package com.microservice.msavaliador.infra.clients;

import com.microservice.msavaliador.domain.Card;
import com.microservice.msavaliador.domain.CartaoCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscard", path = "/cards")
public interface CardsResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf);

    @GetMapping(params="renda")
    public ResponseEntity<List<Card>> getCartoesRendaAte(@RequestParam("renda") Long renda);
}
