package by.epam.javaweb.glazko.multithreading.action;

import by.epam.javaweb.glazko.multithreading.exception.FileWritingException;
import by.epam.javaweb.glazko.multithreading.menu.RestaurantMenu;
import by.epam.javaweb.glazko.multithreading.restaurant.McDonalds;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InitialValuesGenerator {

    private static final InitialValuesGenerator INSTANCE = new InitialValuesGenerator();

    public static InitialValuesGenerator getInstance() {
        return INSTANCE;
    }

    private InitialValuesGenerator() {}

    private McDonalds mcDonalds = McDonalds.getInstance();
    private RestaurantMenu menu = mcDonalds.getMenu();

    /**
     * Generates initial values (customer's parameters: number of order window, quantity
     * of menu item, menu item (1-5 times), order/pre-order) and writes them into file.
     * @param path path to the file to write information in.
     * @throws FileWritingException if there was an error while writing into a file.
     */
    public void generateInitialValues(String path) throws FileWritingException {
        int numberOfCustomers = (int) (Math.random() * 100) + 1;
        List<String> menuItems = menu.getItems();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numberOfCustomers; i++) {
            int cashRegisterNumber = (int) (Math.random() * 3 + 1.5);
            stringBuilder.append(cashRegisterNumber);
            stringBuilder.append(" ");
            for (int j = 0; j < 5; j++) {
                int menuItemQuantity = (int) (Math.random() * 3);
                if (j == 4) {
                    menuItemQuantity++;
                }
                if (menuItemQuantity > 0) {
                    stringBuilder.append(menuItemQuantity);
                    stringBuilder.append(" ");
                    stringBuilder.append(menuItems.get(j));
                    stringBuilder.append(" ");
                }
            }
            int isOrder = (int) (Math.random() + 0.75);
            if (isOrder == 1) {
                stringBuilder.append("Order");
            } else {
                stringBuilder.append("Pre-order");
            }
            stringBuilder.append("\n");
        }
        try (FileWriter writer = new FileWriter(path, false)) {
            String text = stringBuilder.toString();
            writer.write(text);
        } catch (IOException e) {
            throw new FileWritingException("Error while writing initial values to file!", e);
        }
    }
}
