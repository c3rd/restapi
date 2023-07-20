package restapi.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restapi.restapi.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
