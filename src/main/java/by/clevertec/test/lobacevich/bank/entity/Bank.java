package by.clevertec.test.lobacevich.bank.entity;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString(callSuper = true)
public class Bank extends Entity {

    @NonNull
    private String name;
    @NonNull
    private String address;

    public Bank(long id) {
        this.id = id;
    }
}
