package by.epam.javaweb.glazko.multithreading.entity;

import by.epam.javaweb.glazko.multithreading.action.OrderUpdateObserver;
import by.epam.javaweb.glazko.multithreading.exception.WrongActionException;
import by.epam.javaweb.glazko.multithreading.items.MenuItem;
import by.epam.javaweb.glazko.multithreading.state.CompositionState;
import by.epam.javaweb.glazko.multithreading.state.OrderState;

import java.util.HashMap;
import java.util.Map;

public class Order {

    private int number;
    private OrderType type;
    private Map<MenuItem, Integer> orderMenuItems;
    private OrderState orderState;
    private OrderUpdateObserver observer = OrderUpdateObserver.getInstance();
    private double price;

    public Order(OrderType type) {
        this.orderMenuItems = new HashMap<>();
        this.type = type;
        orderState = CompositionState.getInstance();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public OrderType getType() {
        return type;
    }

    public Map<MenuItem, Integer> getOrderMenuItems() {
        return orderMenuItems;
    }

    public void nextState() throws WrongActionException {
        orderState.next(this);
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void addMenuItem(MenuItem menuItem, int quantity) {
        orderMenuItems.put(menuItem, quantity);
        observer.handleEvent(this);
    }

    public String getStatus() {
        return "Status: " + orderState.getStatus(this);
    }
}