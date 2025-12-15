package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.enums.AccountStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter @AllArgsConstructor
public class UpdateAccountStatusCommand {
    @TargetAggregateIdentifier
    private String id;
    private AccountStatus status;
}