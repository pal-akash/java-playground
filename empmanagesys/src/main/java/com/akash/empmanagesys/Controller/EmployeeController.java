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

    @RequestMapping("/update")
    public ResponseEntity<Employee> updateEmployeeById(@RequestParam("id") String employeeId, @RequestBody Employee employee){
        Optional<Employee> foundEmployee = employeeRepository.findById(employeeId);


        foundEmployee.get().setEmployeeId(employee.getEmployeeId());
        foundEmployee.get().setEmployeeName(employee.getEmployeeName());
        foundEmployee.get().setPhoneNumber(employee.getPhoneNumber());
        foundEmployee.get().setEmail(employee.getEmail());
        foundEmployee.get().setReportsTo(employee.getReportsTo());
        foundEmployee.get().setProfileImageUrl(employee.getProfileImageUrl());

        return new ResponseEntity<Employee>(employeeRepository.save(foundEmployee.get()), HttpStatus.OK);
    }

    @RequestMapping("/nthlevelmanager")
    public ResponseEntity<Optional<Employee>> getNthLevelManager(@RequestParam("id") String employeeId, @RequestParam("level") int n) {

        for(int i=0;  i<n; i++){
            Employee employee = employeeRepository.findById(employeeId).get();
            String managerId= employee.getReportsTo();
            employeeId = managerId;
        }

        return new ResponseEntity<Optional<Employee>>(employeeRepository.findById(employeeId), HttpStatus.OK);
    }

    @RequestMapping("/getallempsorted")
    public ResponseEntity<List<Employee>> getEmployeesWithPaginationAndSorting(@RequestParam("pagenumber") int pageNumber, @RequestParam("pagesize") int pageSize, @RequestParam("sortby") String sortBy) {
        return new ResponseEntity<List<Employee>>(employeeService.getAllEmployeeSorted(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy))), HttpStatus.OK);
    }
