package com.microservice.mscard.application;

import com.microservice.mscard.domain.ClienteCartao;
import com.microservice.mscard.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository clienteCartaoRepository;

    public List<ClienteCartao> listCardByCpf(String cpf){
        return clienteCartaoRepository.findByCpf(cpf);
    }
}
