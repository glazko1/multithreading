package by.epam.javaweb.glazko.multithreading.state;

import by.epam.javaweb.glazko.multithreading.entity.Order;
import by.epam.javaweb.glazko.multithreading.exception.WrongActionException;

public interface OrderState {

    void previous(Order order) throws WrongActionException;
    void next(Order order) throws WrongActionException;
    String getStatus(Order order);
}
