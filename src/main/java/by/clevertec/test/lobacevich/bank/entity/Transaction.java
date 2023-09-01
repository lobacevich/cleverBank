package by.clevertec.test.lobacevich.bank.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Transaction extends Entity {

    private long accountSenderId;
    private long accountReceiverId;
    private LocalDateTime dateTime = LocalDateTime.now();
    @NonNull
    private BigDecimal sum;

    public Transaction(long id) {
        this.id = id;
    }
}
