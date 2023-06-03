package com.akash5.studentcrud.service;

import com.akash5.studentcrud.model.Student;

import java.util.List;

public interface StudentService {
    public Student saveStudent(Student student);
    public List<Student> getAllStudent();
    public String deleteById(Integer id);
    public Student getById(Integer id);
    public Student updateById(Student newStudent, Integer id);
}
