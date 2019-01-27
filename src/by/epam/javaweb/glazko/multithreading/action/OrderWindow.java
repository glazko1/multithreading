package by.epam.javaweb.glazko.multithreading.action;

import by.epam.javaweb.glazko.multithreading.entity.Order;
import by.epam.javaweb.glazko.multithreading.entity.OrderType;
import by.epam.javaweb.glazko.multithreading.exception.WrongActionException;
import by.epam.javaweb.glazko.multithreading.factory.MenuItemFactory;
import by.epam.javaweb.glazko.multithreading.items.MenuItem;
import by.epam.javaweb.glazko.multithreading.restaurant.McDonalds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderWindow {

    private static final Logger LOGGER = LogManager.getLogger(OrderWindow.class);
    private static final String LOG_FORMAT = "%-14s | %-12s | %s";

    private int orderWindowNumber;
    private Order currentOrder;
    private Lock lock = new ReentrantLock();
    private MenuItemFactory factory = MenuItemFactory.getInstance();

    public OrderWindow(int orderWindowNumber) {
        this.orderWindowNumber = orderWindowNumber;
    }

    public void takeOrder(Map<String, Integer> menuItems) throws WrongActionException {
        try {
            lock.lock();
            int orderNumber = McDonalds.getOrderQuantity();
            currentOrder = new Order(OrderType.ORDER);
            currentOrder.setNumber(orderNumber);
            LOGGER.info(String.format(LOG_FORMAT, "Order Window " + orderWindowNumber, "Order " + currentOrder.getNumber(), "Hello! Make your order please!"));
            menuItems.forEach(this::addMenuItem);
            processOrder();
        } catch (WrongActionException e) {
            throw new WrongActionException("Order " + currentOrder.getNumber() + ": " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private void addMenuItem(String name, int quantity) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MenuItem menuItem = factory.createMenuItem(name);
        currentOrder.addMenuItem(menuItem, quantity);
        double price = Math.round(quantity * menuItem.getPrice() * 100.0) / 100.0;
        LOGGER.info(String.format(LOG_FORMAT, "Order Window " + orderWindowNumber, "Order " + currentOrder.getNumber(), quantity + " x " + menuItem.getName() + " " + price));
    }

    private void processOrder() throws WrongActionException {
        double price = Math.round(currentOrder.getPrice() * 100.0) / 100.0;
        LOGGER.info(String.format(LOG_FORMAT, "Order Window " + orderWindowNumber, "Order " + currentOrder.getNumber(), "Total price: " + price));
        moveOrderToNextStatus();
        Map<MenuItem, Integer> menuItems = currentOrder.getOrderMenuItems();
        menuItems.forEach((m, q) -> {
            try {
                TimeUnit.SECONDS.sleep(m.getPackageTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info(String.format(LOG_FORMAT, "Order Window " + orderWindowNumber, "Order " + currentOrder.getNumber(), q + " x " + m.getName() + " was brought!"));
        });
        moveOrderToNextStatus();
        LOGGER.info(String.format(LOG_FORMAT, "Order Window " + orderWindowNumber, "Order " + currentOrder.getNumber(), "Thank you for your order! Have a nice day!"));
    }

    private void moveOrderToNextStatus() throws WrongActionException {
        currentOrder.nextState();
        LOGGER.info(String.format(LOG_FORMAT, "", "Order " + currentOrder.getNumber(), currentOrder.getStatus()));
    }
}
