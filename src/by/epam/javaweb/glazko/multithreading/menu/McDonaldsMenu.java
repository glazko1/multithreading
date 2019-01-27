package by.epam.javaweb.glazko.multithreading.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class McDonaldsMenu implements RestaurantMenu {

    private static final McDonaldsMenu INSTANCE = new McDonaldsMenu();

    public static McDonaldsMenu getInstance() {
        return INSTANCE;
    }

    private McDonaldsMenu() {}

    private List<String> menuItems;

    /**
     * Initializes McDonald's menu with names of menu items.
     */
    public void init() {
        menuItems = new ArrayList<>(Arrays.asList(
                "Hamburger",
                "McChicken",
                "FrenchFries",
                "McFlurry",
                "CocaCola"
        ));
    }

    @Override
    public List<String> getItems() {
        return menuItems;
    }
}
