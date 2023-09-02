package by.clevertec.test.lobacevich.bank.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenuItem {

    private String title;
    private final IAction action;
    private Menu nextMenu;

    public void doAction() {
        action.execute();
    }
}
