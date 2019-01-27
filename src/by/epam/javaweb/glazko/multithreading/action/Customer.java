package by.epam.javaweb.glazko.multithreading.action;

import by.epam.javaweb.glazko.multithreading.exception.WrongActionException;
import by.epam.javaweb.glazko.multithreading.restaurant.McDonalds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.Callable;

public class Customer implements Callable<Customer> {

    private static final Logger LOGGER = LogManager.getLogger(Customer.class);

    private McDonalds mcDonalds = McDonalds.getInstance();
    private Map<String, Integer> menuItems;
    private int orderWindowNumber;
    private boolean makePreOrder;

    public Customer(Map<String, Integer> menuItems, int cashRegisterNumber, boolean makePreOrder) {
        this.menuItems = menuItems;
        this.orderWindowNumber = cashRegisterNumber;
        this.makePreOrder = makePreOrder;
    }

    @Override
    public Customer call() {
        if (makePreOrder) {
            makePreOrder();
        } else {
            getInQueue();
        }
        return this;
    }

    private void getInQueue() {
        OrderWindow orderWindow = mcDonalds.getOrderWindow(orderWindowNumber);
        try {
            orderWindow.takeOrder(menuItems);
        } catch (WrongActionException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void makePreOrder() {
        PreOrderWindow preOrderWindow = mcDonalds.getPreOrderWindow();
        try {
            preOrderWindow.takePreOrder(menuItems, orderWindowNumber);
        } catch (WrongActionException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public int getOrderWindowNumber() {
        return orderWindowNumber;
    }

    public boolean isPreOrder() {
        return makePreOrder;
    }
}
