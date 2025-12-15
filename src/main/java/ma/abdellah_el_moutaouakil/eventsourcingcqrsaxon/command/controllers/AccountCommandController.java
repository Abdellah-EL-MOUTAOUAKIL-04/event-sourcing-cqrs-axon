package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.controllers;

import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.commands.AddAccountCommand;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.commands.CreditAccountCommand;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.commands.DebitAccountCommand;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.commands.UpdateAccountStatusCommand;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.dtos.AddNewAccountRequestDTO;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.dtos.CreditAccountDTO;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.dtos.DebitAccountDTO;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.dtos.UpdateAccountStatusDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    public AccountCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDTO request) {
        CompletableFuture<String> response = commandGateway.send(new AddAccountCommand(UUID.randomUUID().toString(), request.initialBalance(), request.currency()));
        return response;
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountDTO request){
        CompletableFuture<String> result = this.commandGateway.send(new DebitAccountCommand(
                request.accountId(),
                request.amount()
        ));
        return result;
    }
    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountDTO request){
        CompletableFuture<String> result = this.commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                "MAD"
        ));
        return result;
    }

    @PutMapping("/updateStatus")
    public CompletableFuture<String> updateStatus(@RequestBody UpdateAccountStatusDTO request){
        CompletableFuture<String> result = this.commandGateway.send(new UpdateAccountStatusCommand(
                request.accountId(),
                request.status()
        ));
        return result;
    }
    @GetMapping("/events/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }
}
