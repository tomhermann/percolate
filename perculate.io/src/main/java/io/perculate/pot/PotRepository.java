package io.perculate.pot;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PotRepository extends CrudRepository<Reading, Long> {
}
