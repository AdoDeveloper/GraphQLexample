package com.graphql.example.service;

//import com.graphql.example.entities.Course;
import com.graphql.example.entities.Student;

import java.util.List;

public interface IStudentService {

    Student findById(Long id);

    List<Student> findAll();

    void createStudent(Student student);

    void deleteById(Long id);
    
 // Método para actualizar un estudiante con el curso
 // Student updateStudent(Long id, String newName, String newLastName, Integer newAge, Course newCourse);
    void updateStudent(Student updatedStudent);
}
