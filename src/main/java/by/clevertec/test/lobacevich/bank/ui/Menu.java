package by.clevertec.test.lobacevich.bank.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * меню, через выбор пунктов которого осуществляется взаимодействие с пользователем
 */
@Getter
@AllArgsConstructor
public class Menu {

    @Setter
    private String name;
    private final List<MenuItem> menuItems;

    /**
     * добавляет новый пункт к меню
     * @param item пункт, который добавляется
     */
    public void addItem(MenuItem item) {
        menuItems.add(item);
    }
}
