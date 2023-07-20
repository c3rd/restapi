package restapi.restapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private String description;
    private String status;

}
