package by.clevertec.test.lobacevich.bank.ui;

import by.clevertec.test.lobacevich.bank.di.Singleton;
import lombok.Setter;

import java.util.List;

@Singleton
public class Navigator {

    @Setter
    private Menu currentMenu;

    private Navigator() {
    }

    public void printMenu() {
        System.out.println("\n" + currentMenu.getName());
        System.out.println(0 + "\tВыход из программы");
        List<MenuItem> list = currentMenu.getMenuItems();
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "\t" + list.get(i).getTitle());
        }
    }

    public boolean navigate(Integer index) throws IndexOutOfBoundsException {
        if (index == 0) {
            return false;
        }
        MenuItem item = currentMenu.getMenuItems().get(index - 1);
        item.doAction();
        setCurrentMenu(item.getNextMenu());
        return true;
    }
}
