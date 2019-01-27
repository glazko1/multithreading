package by.epam.javaweb.glazko.multithreading.state;

import by.epam.javaweb.glazko.multithreading.entity.Order;
import by.epam.javaweb.glazko.multithreading.exception.WrongActionException;

public interface OrderState {

    /**
     * Switches order state to the previous one.
     * @param order order to change state.
     * @throws WrongActionException if there is no available previous state
     * for current one.
     */
    void previous(Order order) throws WrongActionException;

    /**
     * Switches order state to the next one.
     * @param order order to change state.
     * @throws WrongActionException if there is no available next state
     * for current one.
     */
    void next(Order order) throws WrongActionException;

    /**
     * @param order order to get status.
     * @return order status.
     */
    String getStatus(Order order);
}
