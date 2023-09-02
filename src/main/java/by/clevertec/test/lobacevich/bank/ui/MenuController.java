package by.clevertec.test.lobacevich.bank.ui;

public class MenuController {

    private static MenuController INSTANCE = new MenuController();
    private Builder builder = Builder.getINSTANCE();
    private Navigator navigator = Navigator.getINSTANCE();
    private ConsoleProcessor consoleProcessor = ConsoleProcessor.getInstance();

    private MenuController() {
    }

    public static MenuController getInstance() {
        return INSTANCE;
    }

    public void run() {
        builder.buildRootMenu();
        navigator.setCurrentMenu(builder.getRootMenu());
        boolean flag = true;
        while (flag) {
            navigator.printMenu();
            Integer index = consoleProcessor.getIntInput();
            try {
                flag = navigator.navigate(index);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Incorrect input, please try again");
            }
        }
    }
}
