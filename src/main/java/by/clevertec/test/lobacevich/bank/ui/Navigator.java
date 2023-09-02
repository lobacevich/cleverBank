package by.clevertec.test.lobacevich.bank.ui;

import java.util.List;

public class Navigator {

    private Menu currentMenu;
    private static final Navigator INSTANCE = new Navigator();

    private Navigator() {
    }

    public static Navigator getINSTANCE() {
        return INSTANCE;
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

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
}
