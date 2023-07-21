package restapi.restapi.service.impl;

import org.springframework.stereotype.Service;
import restapi.restapi.exception.OrderNotFoundException;
import restapi.restapi.model.Employee;
import restapi.restapi.model.Order;
import restapi.restapi.model.OrderStatus;
import restapi.restapi.repository.EmployeeRepository;
import restapi.restapi.repository.OrderRepository;
import restapi.restapi.service.OrderService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
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
        order.setStatus(OrderStatus.IN_PROGRESS);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Override
    public Order updateOrder(Order order, Long id) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));

        existingOrder.setDescription(order.getDescription());
        existingOrder.setStatus(order.getStatus());

        orderRepository.save(existingOrder);

        return existingOrder;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));

        orderRepository.deleteById(id);
    }

    @Override
    public Order cancelOrder(Long id) {

        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (existingOrder.getStatus() == OrderStatus.IN_PROGRESS) {
            existingOrder.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(existingOrder);
            return existingOrder;
        }

        throw new OrderNotFoundException("Method not allowed");

    }

    @Override
    public Order completeOrder(Long id) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (existingOrder.getStatus() == OrderStatus.IN_PROGRESS) {
            existingOrder.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(existingOrder);
            return existingOrder;
        }

        throw new OrderNotFoundException("Method not allowed");
    }
}
