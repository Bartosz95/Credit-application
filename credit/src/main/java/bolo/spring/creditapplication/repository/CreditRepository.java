package bolo.spring.creditapplication.repository;

import bolo.spring.creditapplication.domain.Credit;
import org.springframework.data.repository.CrudRepository;

public interface CreditRepository extends CrudRepository<Credit, Long> {
}
