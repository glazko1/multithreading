package by.epam.javaweb.glazko.multithreading.items;

public class McChicken implements MenuItem {

    @Override
    public String getName() {
        return "McChicken";
    }

    @Override
    public double getPrice() {
        return 4.3;
    }

    @Override
    public int getPackageTime() {
        return 2;
    }
}
