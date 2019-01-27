package by.epam.javaweb.glazko.multithreading.action;

import by.epam.javaweb.glazko.multithreading.entity.Order;
import by.epam.javaweb.glazko.multithreading.items.MenuItem;

import java.util.Map;

public class OrderUpdateObserver {

    private static final OrderUpdateObserver INSTANCE = new OrderUpdateObserver();

    public static OrderUpdateObserver getInstance() {
        return INSTANCE;
    }

    private OrderUpdateObserver() {}

    /**
     * Process of handling event by observer. Recalculates the price of given order. Called
     * when order information was changed (menu item was added).
     * @param order order to recalculate the price.
     */
    public void handleEvent(Order order) {
        double price = 0.0;
        Map<MenuItem, Integer> orderMenuItems = order.getOrderMenuItems();
        for (Map.Entry<MenuItem, Integer> pair : orderMenuItems.entrySet()) {
            MenuItem menuItem = pair.getKey();
            int quantity = pair.getValue();
            double itemPrice = Math.round(quantity * menuItem.getPrice() * 100.0) / 100.0;
            price += itemPrice;
        }
        order.setPrice(price);
    }
}
