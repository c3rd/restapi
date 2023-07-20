package restapi.restapi.service.impl;

import restapi.restapi.exception.OrderNotFoundException;
import restapi.restapi.model.Employee;
import restapi.restapi.model.Order;
import restapi.restapi.repository.EmployeeRepository;
import restapi.restapi.repository.OrderRepository;
import restapi.restapi.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Override
    public Order updateOrder(Order order, Long id) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }
}
