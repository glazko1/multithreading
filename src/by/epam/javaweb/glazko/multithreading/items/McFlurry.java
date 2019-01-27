package by.epam.javaweb.glazko.multithreading.items;

public class McFlurry implements MenuItem {

    @Override
    public String getName() {
        return "McFlurry";
    }

    @Override
    public double getPrice() {
        return 3.0;
    }

    @Override
    public int getPackageTime() {
        return 5;
    }
}
