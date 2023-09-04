package by.clevertec.test.lobacevich.bank.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * пункт меню
 */
@Getter
@Setter
@AllArgsConstructor
public class MenuItem {

    private String title;
    private final IAction action;
    private Menu nextMenu;

    /**
     * действие, которое будет выполнено при выборе данного пункта меню. реализовано функциональным
     * интерфейсом IAction
     */
    public void doAction() {
        action.execute();
    }
}
