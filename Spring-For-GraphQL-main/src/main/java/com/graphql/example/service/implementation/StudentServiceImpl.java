package com.graphql.example.service.implementation;

import com.graphql.example.entities.Student;
import com.graphql.example.persistence.IStudentDAO;
import com.graphql.example.service.IStudentService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private IStudentDAO studentDAO;

    @Override
    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentDAO.findById(id).orElse(null);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return (List<Student>) studentDAO.findAll();
    }

    @Override
    @Transactional
    public void createStudent(Student student) {
        studentDAO.save(student);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        studentDAO.deleteById(id);
    }
    
    @Override
    @Transactional
    public void updateStudent(Student updatedStudent) {
        Student existingStudent = studentDAO.findById(updatedStudent.getId()).orElse(null);

        if (existingStudent != null) {
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setLastName(updatedStudent.getLastName());
            existingStudent.setAge(updatedStudent.getAge());
            existingStudent.setCourse(updatedStudent.getCourse());

            studentDAO.save(existingStudent);
        }
    }

}
