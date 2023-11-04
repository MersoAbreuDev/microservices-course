package com.microservice.msavaliador.application;

import com.microservice.msavaliador.application.ExceptionResponse.DadosClientesNotFoundException;
import com.microservice.msavaliador.application.ExceptionResponse.ErroComunicacaoMicroservicesException;
import com.microservice.msavaliador.domain.CartaoCliente;
import com.microservice.msavaliador.domain.DadosCliente;
import com.microservice.msavaliador.domain.SituacaoCliente;
import com.microservice.msavaliador.infra.clients.CardsResourceClient;
import com.microservice.msavaliador.infra.clients.ClienteResourceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;

    private final CardsResourceClient cardsResourceClient;
    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClientesNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> responseEntity = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cardsResourceClient.getCartoesByCliente(cpf);
            return SituacaoCliente
                    .builder()
                    .cliente(responseEntity.getBody())
                    .cartaoClientes(cartoesResponse.getBody())
                    .build();
        }catch (FeignException.FeignClientException e){
             int status =  e.status();
             if(HttpStatus.NOT_FOUND.value() == status){
                 throw new DadosClientesNotFoundException();
             }
             throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }


    }


}
