package by.clevertec.test.lobacevich.bank.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class User extends Entity {

    @NonNull
    private String firstname;
    @NonNull
    private String lastname;
    @NonNull
    private String passportPersonalNumber;
    @NonNull
    private String address;
}
