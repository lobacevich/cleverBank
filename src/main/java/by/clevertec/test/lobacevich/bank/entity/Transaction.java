package by.clevertec.test.lobacevich.bank.entity;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction extends Entity {

    private Account accountSender;
    private Account accountReceiver;
    private final LocalDateTime dateTime = LocalDateTime.now();
    @NonNull
    private BigDecimal sum;
}
