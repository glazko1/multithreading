package by.epam.javaweb.glazko.multithreading.factory;

import by.epam.javaweb.glazko.multithreading.items.CocaCola;
import by.epam.javaweb.glazko.multithreading.items.FrenchFries;
import by.epam.javaweb.glazko.multithreading.items.Hamburger;
import by.epam.javaweb.glazko.multithreading.items.McChicken;
import by.epam.javaweb.glazko.multithreading.items.McFlurry;
import by.epam.javaweb.glazko.multithreading.items.MenuItem;

public class MenuItemFactory {

    private static final MenuItemFactory INSTANCE = new MenuItemFactory();

    public static MenuItemFactory getInstance() { return INSTANCE; }

    private MenuItemFactory() {}

    /**
     * Creates and returns menu item in accordance with name given as parameter.
     * Can create an object of any class implementing interface {@code MenuItem}.
     * @param name name of menu item to create.
     * @return menu item with given name.
     */
    public MenuItem createMenuItem(String name) {
        switch (name) {
            case "FrenchFries":
                return new FrenchFries();
            case "CocaCola":
                return new CocaCola();
            case "Hamburger":
                return new Hamburger();
            case "McFlurry":
                return new McFlurry();
            case "McChicken":
                return new McChicken();
        }
        throw new RuntimeException("No such item in menu!");
    }
}
