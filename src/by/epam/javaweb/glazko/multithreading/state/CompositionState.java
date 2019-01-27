package by.epam.javaweb.glazko.multithreading.state;

import by.epam.javaweb.glazko.multithreading.entity.Order;
import by.epam.javaweb.glazko.multithreading.exception.WrongActionException;

public class CompositionState implements OrderState {

    private static final CompositionState INSTANCE = new CompositionState();

    public static CompositionState getInstance() {
        return INSTANCE;
    }

    private CompositionState() {}

    /**
     * @param order order to change state.
     * @throws WrongActionException because there is no available previous state for
     * {@code CompositionState}.
     */
    @Override
    public void previous(Order order) throws WrongActionException {
        throw new WrongActionException("No previous state for Composition State");
    }

    /**
     * Switches given order state to {@code PackagingState}.
     * @param order order to change state.
     */
    @Override
    public void next(Order order) {
        order.setOrderState(PackagingState.getInstance());
    }

    @Override
    public String getStatus(Order order) {
        return "composition";
    }
}
