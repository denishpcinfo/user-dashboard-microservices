package com.d3n15.credit_card_service.repository;
;
import com.d3n15.credit_card_service.entity.CreditCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCardEntity, String> {
    List<CreditCardEntity> findByUserId(String userId);
}