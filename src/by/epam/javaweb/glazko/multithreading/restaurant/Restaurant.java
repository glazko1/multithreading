package by.epam.javaweb.glazko.multithreading.restaurant;

import by.epam.javaweb.glazko.multithreading.menu.RestaurantMenu;

public interface Restaurant {

    /**
     * @return restaurant's menu.
     */
    RestaurantMenu getMenu();
}
