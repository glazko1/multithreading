package by.epam.javaweb.glazko.multithreading.restaurant;

import by.epam.javaweb.glazko.multithreading.action.Customer;
import by.epam.javaweb.glazko.multithreading.action.OrderWindow;
import by.epam.javaweb.glazko.multithreading.action.PreOrderWindow;
import by.epam.javaweb.glazko.multithreading.menu.McDonaldsMenu;
import by.epam.javaweb.glazko.multithreading.menu.RestaurantMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class McDonalds implements Restaurant {

    private static final McDonalds INSTANCE = new McDonalds();

    public static McDonalds getInstance() {
        return INSTANCE;
    }

    private McDonalds() {}

    private RestaurantMenu menu = McDonaldsMenu.getInstance();
    private List<OrderWindow> orderWindows = new ArrayList<>();
    private PreOrderWindow preOrderWindow = PreOrderWindow.getInstance();
    private static AtomicInteger orderQuantity = new AtomicInteger(0);
    private List<ExecutorService> orderWindowExecutorServices = new ArrayList<>();
    private ExecutorService preOrderWindowExecutorService = Executors.newSingleThreadExecutor();

    /**
     * Initializes order windows of McDonald's and provides each of them with thread executor.
     */
    public void init() {
        OrderWindow orderWindow1 = new OrderWindow(1);
        OrderWindow orderWindow2 = new OrderWindow(2);
        OrderWindow orderWindow3 = new OrderWindow(3);
        OrderWindow orderWindow4 = new OrderWindow(4);
        Collections.addAll(orderWindows, orderWindow1, orderWindow2, orderWindow3, orderWindow4);
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        ExecutorService executorService3 = Executors.newSingleThreadExecutor();
        ExecutorService executorService4 = Executors.newSingleThreadExecutor();
        Collections.addAll(orderWindowExecutorServices, executorService1, executorService2, executorService3, executorService4);
    }

    @Override
    public RestaurantMenu getMenu() {
        return menu;
    }

    public OrderWindow getOrderWindow(int cashRegisterNumber) {
        return orderWindows.get(cashRegisterNumber - 1);
    }

    public PreOrderWindow getPreOrderWindow() {
        return preOrderWindow;
    }

    public static int getOrderQuantity() {
        return orderQuantity.addAndGet(1);
    }

    public ExecutorService getExecutorService(Customer customer) {
        int cashRegisterNumber = customer.getOrderWindowNumber();
        boolean isPreOrder = customer.isPreOrder();
        if (isPreOrder) {
            return preOrderWindowExecutorService;
        }
        return orderWindowExecutorServices.get(cashRegisterNumber - 1);
    }

    public void shutdownServices() {
        orderWindowExecutorServices.forEach(ExecutorService::shutdown);
        preOrderWindowExecutorService.shutdown();
    }
}
