package by.epam.javaweb.glazko.multithreading.simulator;

import by.epam.javaweb.glazko.multithreading.action.Customer;
import by.epam.javaweb.glazko.multithreading.action.InitialValuesGenerator;
import by.epam.javaweb.glazko.multithreading.creator.CustomerCreator;
import by.epam.javaweb.glazko.multithreading.exception.FileReadingException;
import by.epam.javaweb.glazko.multithreading.exception.FileWritingException;
import by.epam.javaweb.glazko.multithreading.menu.McDonaldsMenu;
import by.epam.javaweb.glazko.multithreading.reader.CustomerFileReader;
import by.epam.javaweb.glazko.multithreading.restaurant.McDonalds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class McDonaldsWorkSimulator implements WorkSimulator {

    private static final McDonaldsWorkSimulator INSTANCE = new McDonaldsWorkSimulator();

    public static McDonaldsWorkSimulator getInstance() {
        return INSTANCE;
    }

    private McDonaldsWorkSimulator() {}

    private static final Logger LOGGER = LogManager.getLogger(McDonaldsWorkSimulator.class);

    private McDonalds mcDonalds = McDonalds.getInstance();
    private McDonaldsMenu menu = McDonaldsMenu.getInstance();
    private InitialValuesGenerator generator = InitialValuesGenerator.getInstance();
    private CustomerFileReader reader = CustomerFileReader.getInstance();
    private CustomerCreator creator = CustomerCreator.getInstance();

    /**
     * Simulates McDonald's work. Calls {@code init()} methods of McDonald's and its menu
     * instances to initialize their fields. Initial values (customer's parameters) are
     * randomly generated and written to file by {@code InitialValuesGenerator}, then they
     * are read by {@code CustomerFileReader} and customers are created by {@code CustomerCreator}.
     * All created customers are added to the list, and with one second interval they are called.
     * In fair order they get into their order window queue or make and get their pre-orders in
     * selected order windows. After one customer's order is ready, next customer in the queue
     * is able to make his order. When all customers are served, executor services are
     * shutdowned. All information about orders, pre-orders and errors is available in log file.
     */
    @Override
    public void simulateWork() {
        mcDonalds.init();
        menu.init();
        try {
            generator.generateInitialValues("./customer_data/customers.txt");
            List<String> initialValues = reader.read("./customer_data/customers.txt");
            List<Customer> customers = new ArrayList<>();
            initialValues.forEach(s -> {
                Customer customer = creator.create(s);
                customers.add(customer);
            });
            customers.forEach(c -> {
                ExecutorService executorService = mcDonalds.getExecutorService(c);
                executorService.submit(c);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage());
                }
            });
        } catch (FileWritingException | FileReadingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        mcDonalds.shutdownServices();
    }
}
