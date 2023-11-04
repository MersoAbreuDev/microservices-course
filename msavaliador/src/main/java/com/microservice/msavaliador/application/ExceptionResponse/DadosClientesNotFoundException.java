package com.microservice.msavaliador.application.ExceptionResponse;

public class DadosClientesNotFoundException extends Exception{

    public DadosClientesNotFoundException(){
        super("Dados do cliente não encontrado para o CPF informado.");
    }
}
