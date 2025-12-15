package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.dtos;

import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.entities.Account;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.entities.AccountOperation;

import java.util.List;

public record AccountStatementResponseDTO(
        Account account,
        List<AccountOperation> operations
) {
}
