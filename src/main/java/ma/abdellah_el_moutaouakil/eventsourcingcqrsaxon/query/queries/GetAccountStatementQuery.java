package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class GetAccountStatementQuery {
    private String accountId;
}
