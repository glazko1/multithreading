package by.epam.javaweb.glazko.multithreading.state;

import by.epam.javaweb.glazko.multithreading.entity.Order;
import by.epam.javaweb.glazko.multithreading.exception.WrongActionException;

public class ReadyState implements OrderState {

    private static final ReadyState INSTANCE = new ReadyState();

    public static ReadyState getInstance() {
        return INSTANCE;
    }

    private ReadyState() {}

    /**
     * Switches given order state to {@code PackagingState}.
     * @param order order to change state.
     */
    @Override
    public void previous(Order order) {
        order.setOrderState(PackagingState.getInstance());
    }

    /**
     * @param order order to change state.
     * @throws WrongActionException because there is no available next state for
     * {@code CompositionState}.
     */
    @Override
    public void next(Order order) throws WrongActionException {
        throw new WrongActionException("No next state for Ready State");
    }

    @Override
    public String getStatus(Order order) {
        return "ready";
    }
}
