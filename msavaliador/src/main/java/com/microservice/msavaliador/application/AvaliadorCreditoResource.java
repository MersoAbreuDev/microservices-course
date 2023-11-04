package com.microservice.msavaliador.application;
import com.microservice.msavaliador.application.ExceptionResponse.DadosClientesNotFoundException;
import com.microservice.msavaliador.application.ExceptionResponse.ErroComunicacaoMicroservicesException;
import com.microservice.msavaliador.domain.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("avaliacoes-credito")
@RestController
@RequiredArgsConstructor
public class AvaliadorCreditoResource {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping(value="situacao-cliente", params="cpf")
    public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf) throws DadosClientesNotFoundException, ErroComunicacaoMicroservicesException {
        SituacaoCliente situacaoCliente = null;
        try{
            situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        }catch (DadosClientesNotFoundException e){
          return ResponseEntity.notFound().build();
        }catch (ErroComunicacaoMicroservicesException e){
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }
}
