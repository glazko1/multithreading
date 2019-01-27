package by.epam.javaweb.glazko.multithreading.factory;

import by.epam.javaweb.glazko.multithreading.items.*;

public class MenuItemFactory {

    private static final MenuItemFactory INSTANCE = new MenuItemFactory();

    public static MenuItemFactory getInstance() { return INSTANCE; }

    private MenuItemFactory() {}

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
