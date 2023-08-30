package by.clevertec.test.lobacevich.bank.entity;

import by.clevertec.test.lobacevich.bank.entity.enums.Currency;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Account extends Entity {

    @NonNull
    private long userId;
    @NonNull
    private long bankId;
    @NonNull
    private Currency currency;
    @NonNull
    private Long accountNumber;
    private final LocalDateTime date = LocalDateTime.now();
    private BigDecimal balance = BigDecimal.ZERO;
}
