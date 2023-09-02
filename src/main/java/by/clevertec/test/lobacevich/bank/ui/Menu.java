package by.clevertec.test.lobacevich.bank.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Menu {

    @Setter
    private String name;
    private final List<MenuItem> menuItems;

    public void addItem(MenuItem item) {
        menuItems.add(item);
    }
}
