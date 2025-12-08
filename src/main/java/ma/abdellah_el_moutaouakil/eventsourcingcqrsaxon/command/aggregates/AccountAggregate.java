package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.aggregates;

import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.commands.AddAccountCommand;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.enums.AccountStatus;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus status;

    //Pour Axon Framework
    public AccountAggregate(){}

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        if(command.getInitialBalance()<=0) throw new IllegalArgumentException("Initial Balance must be positive");
        System.out.println("*********************AddAccountCommand received*********************");
        AggregateLifecycle.apply(new AccountCreatedEvent(command.getId(),command.getInitialBalance(), AccountStatus.CREATED, command.getCurrency()));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        System.out.println("*********************AccountCreatedEvent received*********************");
        this.accountId=event.getAccountId();
        this.balance=event.getInitialBalance();
        this.status=event.getAccountStatus();
    }
}
