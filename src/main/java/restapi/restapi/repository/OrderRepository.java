package restapi.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restapi.restapi.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
