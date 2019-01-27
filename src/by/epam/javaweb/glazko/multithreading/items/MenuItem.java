package by.epam.javaweb.glazko.multithreading.items;

public interface MenuItem {

    /**
     * @return name of the menu item.
     */
    String getName();

    /**
     * @return price of the menu item.
     */
    double getPrice();

    /**
     * @return package time of the menu item.
     */
    int getPackageTime();
}
