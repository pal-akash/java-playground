package com.akash.empmanagesys.Controller;

import com.akash.empmanagesys.Model.Employee;
import com.akash.empmanagesys.Repository.EmployeeRepository;
import com.akash.empmanagesys.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeRepository.save(employee), HttpStatus.CREATED);
    }

    @RequestMapping("/getbyid")
    public ResponseEntity<Optional<Employee>> getEmployeeById(@RequestParam("id") String employeeId){
        try {
            return new ResponseEntity<Optional<Employee>>(employeeRepository.findById(employeeId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        try {
            return new ResponseEntity<List<Employee>>(employeeRepository.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/deletebyid")
    public ResponseEntity<?> deleteEmployeeById(@RequestParam("id") String employeeId) {
        employeeRepository.deleteById(employeeId);
        return ResponseEntity.ok("Employee deleted!");
    }
