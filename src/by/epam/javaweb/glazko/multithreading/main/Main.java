package by.epam.javaweb.glazko.multithreading.main;

import by.epam.javaweb.glazko.multithreading.simulator.McDonaldsWorkSimulator;
import by.epam.javaweb.glazko.multithreading.simulator.WorkSimulator;

public class Main {

    public static void main(String[] args) {
        WorkSimulator simulator = McDonaldsWorkSimulator.getInstance();
        simulator.simulateWork();
    }
}
