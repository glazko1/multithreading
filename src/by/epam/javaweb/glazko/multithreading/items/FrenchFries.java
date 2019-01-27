package by.epam.javaweb.glazko.multithreading.items;

public class FrenchFries implements MenuItem {

    @Override
    public String getName() {
        return "French Fries";
    }

    @Override
    public double getPrice() {
        return 3.0;
    }

    @Override
    public int getPackageTime() {
        return 4;
    }
}
