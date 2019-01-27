package by.epam.javaweb.glazko.multithreading.creator;

import by.epam.javaweb.glazko.multithreading.action.Customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerCreator {

    private static final CustomerCreator INSTANCE = new CustomerCreator();

    public static CustomerCreator getInstance() {
        return INSTANCE;
    }

    private CustomerCreator() {}

    /**
     * Creates and returns a customer in accordance with given initial values (number
     * of order window, quantity of menu item, menu item (1-5 times), order/pre-order).
     * @param initialValues string with information about the customer.
     * @return customer with given parameters.
     */
    public Customer create(String initialValues) {
        List<String> parameterList = formParameterList(initialValues);
        int cashRegisterNumber = Integer.parseInt(parameterList.get(0));
        boolean makePreOrder = "Pre-order".equals(parameterList.get(parameterList.size() - 1));
        Map<String, Integer> menuItems = formMenuItemsMap(parameterList.subList(1, parameterList.size() - 1));
        return new Customer(menuItems, cashRegisterNumber, makePreOrder);
    }

    /**
     * Creates and returns a list of splitted given parameters. Called from {@code create()}
     * method.
     * @param initialValues string with information about the customer.
     * @return list of parameters.
     */
    private List<String> formParameterList(String initialValues) {
        String[] parameters = initialValues.split(" ");
        return new ArrayList<>(Arrays.asList(parameters));
    }

    /**
     * Creates and returns a map with menu items as keys and their quantities as values. Called
     * from {@code create()} method.
     * @param parameterList list of strings with information about menu items and their
     * quantities.
     * @return map with menu items and their quantities.
     */
    private Map<String, Integer> formMenuItemsMap(List<String> parameterList) {
        Map<String, Integer> menuItems = new HashMap<>();
        while (!parameterList.isEmpty()) {
            String quantityText = parameterList.get(0);
            int quantity = Integer.parseInt(quantityText);
            String menuItem = parameterList.get(1);
            menuItems.put(menuItem, quantity);
            parameterList.remove(0);
            parameterList.remove(0);
        }
        return menuItems;
    }
}
