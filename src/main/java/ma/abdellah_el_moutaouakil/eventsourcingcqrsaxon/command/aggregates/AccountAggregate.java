package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.aggregates;

import lombok.Getter;
import lombok.Setter;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.commands.AddAccountCommand;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.commands.CreditAccountCommand;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.commands.DebitAccountCommand;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.commands.UpdateAccountStatusCommand;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.enums.AccountStatus;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.events.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Getter
@Setter
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
        AggregateLifecycle.apply(new AccountActivatedEvent(command.getId(),AccountStatus.ACTIVATED));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        System.out.println("*********************AccountCreatedEvent received*********************");
        this.accountId=event.getAccountId();
        this.balance=event.getInitialBalance();
        this.status=event.getAccountStatus();
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        System.out.println("*********************AccountActivatedEvent received*********************");
        this.accountId=event.getAccountId();
        this.status=event.getAccountStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command){
        System.out.println("*********************CreditAccountCommand received*********************");
        if (!this.getStatus().equals(AccountStatus.ACTIVATED)) throw  new RuntimeException("This account can not be debited because of the account is not activated. The current status is "+status);
        if(command.getAmount()<=0) throw new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        System.out.println("*********************AccountCreditedEvent received*********************");
        this.accountId=event.getAccountId();
        this.balance+=event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command){
        System.out.println("*********************DebitAccountCommand received*********************");
        if (!this.getStatus().equals(AccountStatus.ACTIVATED)) throw  new RuntimeException("This account can not be debited because of the account is not activated. The current status is "+status);
        if(balance<command.getAmount()) throw new IllegalArgumentException("balance must be greater than amount to debit");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                "MAD"
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        System.out.println("*********************AccountDebitedEvent received*********************");
        this.accountId=event.getAccountId();
        this.balance-=event.getAmount();
    }

    @CommandHandler
    public void handle(UpdateAccountStatusCommand command){
        System.out.println("*********************UpdateAccountStatusCommand received*********************");
        if(command.getStatus()==status) throw new RuntimeException("this account is already "+status);
        AggregateLifecycle.apply(new AccountUpdateStatusEvent(
                command.getId(),
                command.getStatus()
        ));
    }

    @EventSourcingHandler
    public void on(AccountUpdateStatusEvent event){
        System.out.println("*********************UpdateAccountStatusEvent received*********************");
        this.accountId=event.getAccountId();
        this.status=event.getStatus();
    }
}
