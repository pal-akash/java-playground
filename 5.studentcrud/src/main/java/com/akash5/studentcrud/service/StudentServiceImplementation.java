package com.akash5.studentcrud.service;

import com.akash5.studentcrud.model.Student;
import com.akash5.studentcrud.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentServiceImplementation implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }
}
