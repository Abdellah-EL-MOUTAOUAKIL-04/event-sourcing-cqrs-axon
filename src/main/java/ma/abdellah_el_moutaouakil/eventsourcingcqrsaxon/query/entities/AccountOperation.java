package ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.abdellah_el_moutaouakil.eventsourcingcqrsaxon.query.enums.OperationType;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AccountOperation {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Instant date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    private String currency;
    @ManyToOne
    private Account account;
}
