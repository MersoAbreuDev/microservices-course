package com.microservice.mscard.application;

import com.microservice.mscard.application.represetation.CardRequest;
import com.microservice.mscard.application.represetation.ClienteCartaoResponse;
import com.microservice.mscard.domain.Card;
import com.microservice.mscard.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cards")
@RequiredArgsConstructor
public class CardResource {

    private final CardService cardService;
    private final  ClienteCartaoService clienteCartaoService;

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CardRequest cardRequest){
        Card card = cardRequest.toModel();
        cardService.save(card);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params="renda")
    public ResponseEntity<List<Card>> getCartoesRendaAte(@RequestParam("renda") Long renda){
        List<Card> list = cardService.getCartoesrendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }
    @GetMapping(params ="cpf")
    public ResponseEntity<List<ClienteCartaoResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf){
        List<ClienteCartao> lista = clienteCartaoService.listCardByCpf(cpf);
        List<ClienteCartaoResponse> resultList = lista.stream().map(ClienteCartaoResponse::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }
}
