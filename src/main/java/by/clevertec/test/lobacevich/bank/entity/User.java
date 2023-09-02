package by.clevertec.test.lobacevich.bank.entity;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString(callSuper = true)
public class User extends Entity {

    @NonNull
    private String firstname;
    @NonNull
    private String lastname;
    @NonNull
    private String surname;
    @NonNull
    private String passportPersonalNumber;
    @NonNull
    private String address;

    public User(long id) {
        this.id = id;
    }
}
