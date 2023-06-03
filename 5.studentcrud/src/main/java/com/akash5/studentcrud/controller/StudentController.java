package com.akash5.studentcrud.controller;

import com.akash5.studentcrud.exception.UserNotFoundException;
import com.akash5.studentcrud.model.Student;
import com.akash5.studentcrud.repository.StudentRepository;
import com.akash5.studentcrud.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    private StudentRepository studentRepository;

    @PostMapping("/add")
    public String add(@RequestBody Student student){
        studentService.saveStudent(student);
        return "New student is added";
    }

    @GetMapping("/getAll")
    public List<Student> getAllStudent(){
        return studentService.getAllStudent();
    }

    @GetMapping("/getById/{id}")
    public Student getById(@PathVariable Integer id){
        return studentService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        return studentService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public Student update(@RequestBody Student newStudent, @PathVariable Integer id){
        return studentService.updateById(newStudent, id);
    }



}
