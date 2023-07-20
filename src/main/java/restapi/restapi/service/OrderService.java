package restapi.restapi.service;

import restapi.restapi.model.Employee;
import restapi.restapi.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();
    Order saveOrder(Order order);

    Order getOrderById(Long id);

    Order updateOrder(Order order, Long id);

    void deleteOrder(Long id);

}
