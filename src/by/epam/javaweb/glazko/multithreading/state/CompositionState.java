package by.epam.javaweb.glazko.multithreading.state;

import by.epam.javaweb.glazko.multithreading.entity.Order;
import by.epam.javaweb.glazko.multithreading.exception.WrongActionException;

public class CompositionState implements OrderState {

    private static final CompositionState INSTANCE = new CompositionState();

    public static CompositionState getInstance() {
        return INSTANCE;
    }

    private CompositionState() {}

    @Override
    public void previous(Order order) throws WrongActionException {
        throw new WrongActionException("No previous state for Composition State");
    }

    @Override
    public void next(Order order) {
        order.setOrderState(PackagingState.getInstance());
    }

    @Override
    public String getStatus(Order order) {
        return "composition";
    }
}
