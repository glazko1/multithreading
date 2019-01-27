package by.epam.javaweb.glazko.multithreading.state;

import by.epam.javaweb.glazko.multithreading.entity.Order;

public class PackagingState implements OrderState {

    private static final PackagingState INSTANCE = new PackagingState();

    public static PackagingState getInstance() {
        return INSTANCE;
    }

    private PackagingState() {}

    @Override
    public void previous(Order order) {
        order.setOrderState(CompositionState.getInstance());
    }

    @Override
    public void next(Order order) {
        order.setOrderState(ReadyState.getInstance());
    }

    @Override
    public String getStatus(Order order) {
        return "packaging";
    }
}
