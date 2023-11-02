package com.cursomicroservices.msclientes.application;


import com.cursomicroservices.msclientes.application.represetation.ClienteSaveRequest;
import com.cursomicroservices.msclientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteResource {

    private final ClienteService clienteService;
    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request){
        Cliente model = request.toModel();
        clienteService.save(model);

        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(model.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping
    public String status(){
        log.info("obtendo o status do microservices de clientes");
        return "Teste de ms";
    }
    @GetMapping(params="cpf")
    public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf){
        var cliente = clienteService.getbyCpf(cpf);
        if(cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }
}
