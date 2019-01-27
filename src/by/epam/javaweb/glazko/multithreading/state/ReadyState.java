package by.epam.javaweb.glazko.multithreading.state;

import by.epam.javaweb.glazko.multithreading.entity.Order;
import by.epam.javaweb.glazko.multithreading.exception.WrongActionException;

public class ReadyState implements OrderState {

    private static final ReadyState INSTANCE = new ReadyState();

    public static ReadyState getInstance() {
        return INSTANCE;
    }

    private ReadyState() {}

    @Override
    public void previous(Order order) {
        order.setOrderState(PackagingState.getInstance());
    }

    @Override
    public void next(Order order) throws WrongActionException {
        throw new WrongActionException("No next state for Ready State");
    }

    @Override
    public String getStatus(Order order) {
        return "ready";
    }
}
