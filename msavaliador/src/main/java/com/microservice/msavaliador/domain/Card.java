package com.microservice.msavaliador.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Card {


    private Long id;
    private String nome;
    private String bandeira;
    private BigDecimal limiteBasico;
}
