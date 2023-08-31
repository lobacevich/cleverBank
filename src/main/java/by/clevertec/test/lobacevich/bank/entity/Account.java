package by.clevertec.test.lobacevich.bank.entity;

import by.clevertec.test.lobacevich.bank.entity.enums.Currency;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Account extends Entity {

    private User user;
    private Bank bank;
    @NonNull
    private Currency currency;
    private int accountNumber;
    private final LocalDateTime creationDate = LocalDateTime.now();
    private BigDecimal balance = BigDecimal.ZERO;
}
