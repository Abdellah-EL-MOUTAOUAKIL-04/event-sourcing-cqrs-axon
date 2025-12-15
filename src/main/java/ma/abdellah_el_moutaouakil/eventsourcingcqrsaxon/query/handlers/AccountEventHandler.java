package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.handlers;

import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.events.*;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.entities.Account;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.entities.AccountOperation;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.enums.OperationType;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.repositories.AccountRepository;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.repositories.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;

@Component
public class AccountEventHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private QueryUpdateEmitter queryUpdateEmitter;

    public AccountEventHandler(AccountRepository accountRepository, OperationRepository operationRepository, QueryUpdateEmitter queryUpdateEmitter) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage) {
        System.out.println("===================Query Side - Account Created Event Handled===================");
        Account account= Account.builder()
                .id(event.getAccountId())
                .balance(event.getInitialBalance())
                .currency(event.getCurrency())
                .status(event.getAccountStatus())
                .createdAt(eventMessage.getTimestamp())
                .build();
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountActivatedEvent event){
        System.out.println("===================Query Side - Account Activated Event Handled===================");
        Account account=accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getAccountStatus());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountUpdateStatusEvent event){
        System.out.println("===================Query Side - Account Update Status Event Handled===================");
        Account account=accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event,EventMessage eventMessage) {
        System.out.println("===================Query Side - Account Debited Event Handled===================");
        Account account=accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation= AccountOperation.builder()
                .type(OperationType.DEBIT)
                .amount(event.getAmount())
                .account(account)
                .date(eventMessage.getTimestamp())
                .account(account)
                .build();
        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance()- event.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(e->true,accountOperation);
    }

    @EventHandler
    public void on(AccountCreditedEvent event,EventMessage eventMessage) {
        System.out.println("===================Query Side - Account Credited Event Handled===================");
        Account account=accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation= AccountOperation.builder()
                .type(OperationType.CREDIT)
                .amount(event.getAmount())
                .account(account)
                .date(eventMessage.getTimestamp())
                .account(account)
                .build();
        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance()+ event.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(e->true,accountOperation);
    }
}
