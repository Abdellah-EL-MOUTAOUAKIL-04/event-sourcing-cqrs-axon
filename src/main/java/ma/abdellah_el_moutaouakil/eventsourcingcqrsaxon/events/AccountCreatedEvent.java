package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.enums.AccountStatus;

@Getter @AllArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private AccountStatus accountStatus;
    private String currency;
}
