package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AccountCreditedEvent {
    private String accountId;
    private double amount;
    private String currency;
}