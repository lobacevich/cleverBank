package by.clevertec.test.lobacevich.bank.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
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

    public Account(long id) {
        this.id = id;
    }
}
