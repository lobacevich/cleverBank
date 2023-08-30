package by.clevertec.test.lobacevich.bank.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class Bank extends Entity {

    @NonNull
    private String name;
    @NonNull
    private String address;
}
