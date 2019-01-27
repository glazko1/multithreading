package by.epam.javaweb.glazko.multithreading.simulator;

import by.epam.javaweb.glazko.multithreading.action.Customer;
import by.epam.javaweb.glazko.multithreading.action.InitialValuesGenerator;
import by.epam.javaweb.glazko.multithreading.creator.CustomerCreator;
import by.epam.javaweb.glazko.multithreading.exception.FileReadingException;
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

    @Override
    public void simulateWork() {
        mcDonalds.init();
        menu.init();
        generator.generateInitialValues("./customer_data/customers.txt");
        List<String> initialValues;
        try {
            initialValues = reader.read("./customer_data/customers.txt");
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
                    e.printStackTrace();
                }
            });
        } catch (FileReadingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        mcDonalds.shutdownServices();
    }
}
