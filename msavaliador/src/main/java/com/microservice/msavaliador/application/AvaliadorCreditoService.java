package com.microservice.msavaliador.application;

import com.microservice.msavaliador.application.ExceptionResponse.DadosClientesNotFoundException;
import com.microservice.msavaliador.application.ExceptionResponse.ErroComunicacaoMicroservicesException;
import com.microservice.msavaliador.domain.*;
import com.microservice.msavaliador.infra.clients.CardsResourceClient;
import com.microservice.msavaliador.infra.clients.ClienteResourceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    public RetornoAvaliacaoCliente realizarAvalizacao(String cpf, Long renda) throws DadosClientesNotFoundException, ErroComunicacaoMicroservicesException{
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<Card>> cartoesResponse = cardsResourceClient.getCartoesRendaAte(renda);

            List<Card> cartoes = cartoesResponse.getBody();
           var listaCartoesAprovados =  cartoes.stream().map(cartao->{
                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);
                return aprovado;
            }).collect(Collectors.toList());
           return new RetornoAvaliacaoCliente(listaCartoesAprovados);
        }
        catch (FeignException.FeignClientException e){
            int status =  e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClientesNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }


}
