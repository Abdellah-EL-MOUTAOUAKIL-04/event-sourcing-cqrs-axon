package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.controllers;

import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.dtos.AccountStatementResponseDTO;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.entities.Account;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.entities.AccountOperation;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.queries.GetAccountStatementQuery;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.queries.GetAllAccountsQuery;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.queries.WatchEventQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/accounts")
public class AccountQueryController {
    private QueryGateway queryGateway;

    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }
    @GetMapping("/all")
    public CompletableFuture<List<Account>> getAllAccounts() {
        CompletableFuture<List<Account>> result = queryGateway.query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class));
        return result;
    }

    @GetMapping("/accountStatement/{accountId}")
    public CompletableFuture<AccountStatementResponseDTO> getAccountStatement(@PathVariable String accountId) {

        return queryGateway.query(new GetAccountStatementQuery(accountId),ResponseTypes.instanceOf(AccountStatementResponseDTO.class)); // Placeholder return statement
    }

    @GetMapping(value = "/watch/{accountId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountOperation> watch(@PathVariable String accountId){
        SubscriptionQueryResult<AccountOperation, AccountOperation> result = queryGateway.subscriptionQuery(new WatchEventQuery(accountId),
                ResponseTypes.instanceOf(AccountOperation.class),
                ResponseTypes.instanceOf(AccountOperation.class)
        );
        return result.initialResult().concatWith(result.updates());
    }

}
