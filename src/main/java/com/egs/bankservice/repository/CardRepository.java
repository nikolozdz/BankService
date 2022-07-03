package com.egs.bankservice.repository;

import com.egs.bankservice.entity.card.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {

    boolean existsByCardNumber(String cardNumber);

    Card getCardByCardNumber(String cardNumber);
}
