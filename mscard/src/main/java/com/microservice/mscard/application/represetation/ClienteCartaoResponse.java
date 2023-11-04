package com.microservice.mscard.application.represetation;

import com.microservice.mscard.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCartaoResponse {
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static ClienteCartaoResponse fromModel(ClienteCartao model){
        return new ClienteCartaoResponse(
                model.getCard().getNome(),
                model.getCard().getBandeira().toString(),
                model.getLimite()
        );
    }
}
