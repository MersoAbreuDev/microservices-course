package com.microservice.mscard.application.represetation;

import com.microservice.mscard.domain.Card;
import com.microservice.mscard.domain.enums.BandeiraCartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardRequest {
    private String nome;
    private BandeiraCartao bandeira;

    private BigDecimal renda;

    private BigDecimal limiteBasico;

    public Card toModel(){
        return new Card(nome, bandeira, renda, limiteBasico);
    }
}
