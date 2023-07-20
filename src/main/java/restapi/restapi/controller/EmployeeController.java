package restapi.restapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.restapi.model.Employee;
import restapi.restapi.model.EmployeeModelAssembler;
import restapi.restapi.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeModelAssembler assembler;

    EmployeeController(EmployeeService employeeService, EmployeeModelAssembler assembler) {
        this.employeeService = employeeService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Employee>> getAllEmployees() {
        List<EntityModel<Employee>> employees = employeeService
                .getAllEmployees()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel());
    }

    @GetMapping("{id}")
    public EntityModel<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);

        return assembler.toModel(employee);
    }

    @PostMapping
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {

        EntityModel<Employee> newEmployee = assembler.toModel(employeeService.saveEmployee(employee));

        return ResponseEntity.created(newEmployee.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(newEmployee);

    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee, @PathVariable Long id)
    {
        Employee existingEmployee = employeeService.updateEmployee(employee, id);

        EntityModel<Employee> entityModel = assembler.toModel(existingEmployee);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

}

