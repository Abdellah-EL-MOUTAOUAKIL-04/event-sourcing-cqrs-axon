package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.controllers;

import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.commands.AddAccountCommand;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.command.dtos.AddNewAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {
    private CommandGateway commandGateway;

    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDTO request) {
        CompletableFuture<String> response = commandGateway.send(new AddAccountCommand(UUID.randomUUID().toString(), request.initialBalance(), request.currency()));
        return response;
    }
}
