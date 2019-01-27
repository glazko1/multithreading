package by.epam.javaweb.glazko.multithreading.items;

public class Hamburger implements MenuItem {

    @Override
    public String getName() {
        return "Hamburger";
    }

    @Override
    public double getPrice() {
        return 1.8;
    }

    @Override
    public int getPackageTime() {
        return 2;
    }
}
