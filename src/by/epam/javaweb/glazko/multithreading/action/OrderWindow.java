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

    /**
     * Process of taking order from customer. Order window becomes locked when customer calls
     * this method, then new order is created and ordinal number is set to it. For every menu item
     * in given map method {@code addMenuItem()} is called, and then order is processed (menu
     * items are packaged).
     * @param menuItems list of menu items in order.
     * @throws WrongActionException if it's impossible to move order to the next status.
     */
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

    /**
     * Process of adding menu item to the order after sleeping 2 seconds (time to find this menu item
     * and tap on it). After adding information about this item (name and quantity) is printed to the log file.
     * @param name menu item's name.
     * @param quantity menu item's quantity.
     */
    private void addMenuItem(String name, int quantity) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
        MenuItem menuItem = factory.createMenuItem(name);
        currentOrder.addMenuItem(menuItem, quantity);
        double price = Math.round(quantity * menuItem.getPrice() * 100.0) / 100.0;
        LOGGER.info(String.format(LOG_FORMAT, "Order Window " + orderWindowNumber, "Order " + currentOrder.getNumber(), quantity + " x " + menuItem.getName() + " " + price));
    }

    /**
     * Processes customer's order. Sleeps the amount of time needed to bring the menu item, then prints
     * information about packaged item to the log file.
     * @throws WrongActionException if it's impossible to move order to the next status.
     */
    private void processOrder() throws WrongActionException {
        double price = Math.round(currentOrder.getPrice() * 100.0) / 100.0;
        LOGGER.info(String.format(LOG_FORMAT, "Order Window " + orderWindowNumber, "Order " + currentOrder.getNumber(), "Total price: " + price));
        moveOrderToNextStatus();
        Map<MenuItem, Integer> menuItems = currentOrder.getOrderMenuItems();
        menuItems.forEach((m, q) -> {
            try {
                TimeUnit.SECONDS.sleep(m.getPackageTime());
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
            LOGGER.info(String.format(LOG_FORMAT, "Order Window " + orderWindowNumber, "Order " + currentOrder.getNumber(), q + " x " + m.getName() + " was brought!"));
        });
        moveOrderToNextStatus();
        LOGGER.info(String.format(LOG_FORMAT, "Order Window " + orderWindowNumber, "Order " + currentOrder.getNumber(), "Thank you for your order! Have a nice day!"));
    }

    /**
     * Moves order to the next status.
     * @throws WrongActionException if it's impossible to move order to the next status.
     */
    private void moveOrderToNextStatus() throws WrongActionException {
        currentOrder.nextState();
        LOGGER.info(String.format(LOG_FORMAT, "", "Order " + currentOrder.getNumber(), currentOrder.getStatus()));
    }
}
