package by.clevertec.test.lobacevich.bank.entity;

import by.clevertec.test.lobacevich.bank.entity.enums.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Account extends Entity {

    private long userId;
    private long bankId;
    @NonNull
    private Currency currency;
    private int accountNumber;
    private LocalDateTime creationDate;
    private BigDecimal balance;

    public Account(long id) {
        this.id = id;
    }
}
