package by.clevertec.test.lobacevich.bank.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class Transaction extends Entity {

    private Account accountSender;
    private Account accountReceiver;
    private LocalDateTime dateTime = LocalDateTime.now();
    @NonNull
    private BigDecimal sum;

    public Transaction(long id) {
        this.id = id;
    }

    public Transaction(Account accountSender, Account accountReceiver, @NonNull BigDecimal sum) {
        this.accountSender = accountSender;
        this.accountReceiver = accountReceiver;
        this.sum = sum;
    }
}
