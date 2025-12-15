package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.dtos;

import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.enums.AccountStatus;

public record UpdateAccountStatusDTO(String accountId, AccountStatus status) {
}
