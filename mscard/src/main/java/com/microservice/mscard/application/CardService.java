package com.microservice.mscard.application;

import com.microservice.mscard.domain.Card;
import com.microservice.mscard.infra.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    @Transactional
    public Card save(Card card){
        return cardRepository.save(card);
    }

    public List<Card> getCartoesrendaMenorIgual(Long renda){
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        return cardRepository.findByRendaLessThanEqual(rendaBigDecimal);
    }

}
