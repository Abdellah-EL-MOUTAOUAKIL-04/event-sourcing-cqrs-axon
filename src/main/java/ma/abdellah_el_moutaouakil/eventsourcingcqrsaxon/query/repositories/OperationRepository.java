package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.repositories;

import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByAccountId(String accountId);
}
