package by.epam.javaweb.glazko.multithreading.items;

public class CocaCola implements MenuItem {

    @Override
    public String getName() {
        return "Coca Cola";
    }

    @Override
    public double getPrice() {
        return 2.2;
    }

    @Override
    public int getPackageTime() {
        return 3;
    }
}
