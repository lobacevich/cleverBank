package by.clevertec.test.lobacevich.bank.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {

    private String accountNumber;
    private String firstname;
    private String lastname;
    private String surname;
    private Double balance;
}
