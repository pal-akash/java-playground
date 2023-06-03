package com.akash5.studentcrud.service;

import com.akash5.studentcrud.exception.UserNotFoundException;
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

    @Override
    public String deleteById(Integer id) {
        if (!studentRepository.existsById(id)) {
            return "User not found";
        }
        studentRepository.deleteById(id);
        return "User with id " + id + " has been deleted successfully";
    }

    @Override
    public Student getById(Integer id){
        return studentRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    }
}
