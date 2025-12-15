package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.handlers;

import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.dtos.AccountStatementResponseDTO;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.entities.Account;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.entities.AccountOperation;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.queries.GetAccountStatementQuery;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.queries.GetAllAccountsQuery;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.queries.WatchEventQuery;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.repositories.AccountRepository;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.repositories.OperationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountQueryHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery getAllAccountsQuery){
        return accountRepository.findAll();
    }

    @QueryHandler
    public AccountStatementResponseDTO on(GetAccountStatementQuery query){
        Account account=accountRepository.findById(query.getAccountId()).get();
        List<AccountOperation> operations=operationRepository.findByAccountId(account.getId());
        return new AccountStatementResponseDTO(account,operations);
    }

    @QueryHandler
    public AccountOperation on(WatchEventQuery watchEventQuery){
        return AccountOperation.builder().build();
    }
}
