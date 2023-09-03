package by.clevertec.test.lobacevich.bank.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@ToString(callSuper = true)
public class Account extends Entity {

    @NonNull
    private User user;
    @NonNull
    private Bank bank;
    @NonNull
    private String accountNumber;
    private LocalDate creationDate = LocalDate.now();
    private BigDecimal balance = BigDecimal.ZERO;
    private LocalDateTime lastInterest = LocalDateTime.now();

    public Account(long id) {
        this.id = id;
    }
}
