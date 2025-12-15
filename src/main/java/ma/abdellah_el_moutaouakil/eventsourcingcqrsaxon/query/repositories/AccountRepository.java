package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.repositories;

import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
}
