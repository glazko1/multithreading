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

public class PreOrderWindow {

    private static final PreOrderWindow INSTANCE = new PreOrderWindow();

    public static PreOrderWindow getInstance() {
        return INSTANCE;
    }

    private PreOrderWindow() {}

    private static final Logger LOGGER = LogManager.getLogger(PreOrderWindow.class);
    private static final String LOG_FORMAT = "%-14s | %-12s | %s";

    private int orderWindowNumber;
    private Order currentOrder;
    private Lock lock = new ReentrantLock();
    private MenuItemFactory factory = MenuItemFactory.getInstance();

    /**
     * Process of taking pre-order from customer. Pre-order window becomes locked when customer calls
     * this method, then new order is created and ordinal number is set to it. For every menu item
     * in given map method {@code addMenuItem()} is called, and then order is processed (menu
     * items are packaged).
     * @param menuItems list of menu items in order.
     * @throws WrongActionException if it's impossible to move order to the next status.
     */
    public void takePreOrder(Map<String, Integer> menuItems, int cashRegisterNumber) throws WrongActionException {
        try {
            lock.lock();
            this.orderWindowNumber = cashRegisterNumber;
            int orderNumber = McDonalds.getOrderQuantity();
            currentOrder = new Order(OrderType.PRE_ORDER);
            currentOrder.setNumber(orderNumber);
            menuItems.forEach(this::addMenuItem);
            processPreOrder();
        } catch (WrongActionException e) {
            throw new WrongActionException("Pre-order " + currentOrder.getNumber() + ": " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    /**
     * Process of adding menu item to the order. After adding information about this item (name and
     * quantity) is printed to the log file.
     * @param name menu item's name.
     * @param quantity menu item's quantity.
     */
    private void addMenuItem(String name, int quantity) {
        MenuItem menuItem = factory.createMenuItem(name);
        double price = Math.round(quantity * menuItem.getPrice() * 100.0) / 100.0;
        currentOrder.addMenuItem(menuItem, quantity);
        LOGGER.info(String.format(LOG_FORMAT, "", "Pre-order " + currentOrder.getNumber(), quantity + " x " + menuItem.getName() + " " + price));
    }

    /**
     * Processes customer's order. Sleeps the amount of time needed to bring the menu item, then prints
     * information about packaged item to the log file.
     * @throws WrongActionException if it's impossible to move order to the next status.
     */
    private void processPreOrder() throws WrongActionException {
        double price = Math.round(currentOrder.getPrice() * 100.0) / 100.0;
        LOGGER.info(String.format(LOG_FORMAT, "", "Pre-order " + currentOrder.getNumber(), "Total price: " + price));
        moveOrderToNextStatus();
        Map<MenuItem, Integer> menuItems = currentOrder.getOrderMenuItems();
        menuItems.forEach((m, q) -> {
            try {
                TimeUnit.SECONDS.sleep(m.getPackageTime());
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
            LOGGER.info(String.format(LOG_FORMAT, "", "Pre-order " + currentOrder.getNumber(), q + " x " + m.getName() + " was packaged!"));
        });
        moveOrderToNextStatus();
        LOGGER.info(String.format(LOG_FORMAT, "Order Window " + orderWindowNumber, "Pre-order " + currentOrder.getNumber(), "Thank you for your order! Have a nice day!"));
    }

    /**
     * Moves order to the next status.
     * @throws WrongActionException if it's impossible to move order to the next status.
     */
    private void moveOrderToNextStatus() throws WrongActionException {
        currentOrder.nextState();
        LOGGER.info(String.format(LOG_FORMAT, "", "Pre-order " + currentOrder.getNumber(), currentOrder.getStatus()));
    }
}
