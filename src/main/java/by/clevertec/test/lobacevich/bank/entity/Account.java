package by.clevertec.test.lobacevich.bank.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Account extends Entity {

    @NonNull
    private User user;
    @NonNull
    private Bank bank;
    private int accountNumber;
    private LocalDate creationDate = LocalDate.now();
    private BigDecimal balance = BigDecimal.ZERO;

    public Account(long id) {
        this.id = id;
    }

    public Account(@NonNull User user, @NonNull Bank bank, int accountNumber) {
        this.user = user;
        this.bank = bank;
        this.accountNumber = accountNumber;
    }
}
