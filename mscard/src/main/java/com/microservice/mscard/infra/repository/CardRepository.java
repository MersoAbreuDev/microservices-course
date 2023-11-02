package com.microservice.mscard.infra.repository;

import com.microservice.mscard.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CardRepository extends JpaRepository<Card, Long> {
}
