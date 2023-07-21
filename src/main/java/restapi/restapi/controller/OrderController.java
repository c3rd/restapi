package restapi.restapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.restapi.model.Order;
import restapi.restapi.model.OrderModelAssembler;
import restapi.restapi.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderModelAssembler assembler;

    public OrderController(OrderService orderService, OrderModelAssembler assembler) {
        this.orderService = orderService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Order>> getAllOrders() {
        List<EntityModel<Order>> orders = orderService.getAllOrders()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel());
    }

    @GetMapping("{id}")
    public EntityModel<Order> getOrderById(@PathVariable Long id)
    {
        return assembler.toModel(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Order>> saveOrder(@RequestBody Order order) {

        Order newOrder = orderService.saveOrder(order);

        return ResponseEntity.created(linkTo(methodOn(OrderController.class)
                        .getOrderById(newOrder.getId()))
                        .toUri())
                .body(assembler.toModel(newOrder));
    }

    @DeleteMapping("{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {

        Order order = orderService.cancelOrder(id);

        return ResponseEntity.ok(assembler.toModel(order));

    }

    @PutMapping("{id}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable Long id) {

        Order order = orderService.completeOrder(id);

        return ResponseEntity.ok(assembler.toModel(order));

    }


}
