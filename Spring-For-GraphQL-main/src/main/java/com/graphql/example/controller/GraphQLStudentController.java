package com.graphql.example.controller;

import com.graphql.example.entities.Course;
import com.graphql.example.entities.Student;
import com.graphql.example.graphql.InputStudent;
import com.graphql.example.service.ICourseService;
import com.graphql.example.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller

public class GraphQLStudentController {

    @Autowired
    private IStudentService studentService;

    @Autowired
    private ICourseService courseService;

    @QueryMapping(name = "findStudentById")
    public Student findById(@Argument(name = "studentId") String id){
        Long studentId = Long.parseLong(id);

        return studentService.findById(studentId);
    }

    @QueryMapping(name = "findAllStudents")
    public List<Student> findAll(){
        return studentService.findAll();
    }

    @MutationMapping
    public Student createStudent(@Argument InputStudent inputStudent){

        Course course = courseService.findById(Long.parseLong(inputStudent.getCourseId()));

        Student student = new Student();
        student.setName(inputStudent.getName());
        student.setLastName(inputStudent.getLastName());
        student.setAge(inputStudent.getAge());
        student.setCourse(course);

        studentService.createStudent(student);

        return student;
    }

    @MutationMapping(name = "deleteStudentById")
    public String deleteById(@Argument(name = "studentId") String id) {
        try {
            Long studentId = Long.parseLong(id);
            Student deletedStudent = studentService.findById(studentId);

            if (deletedStudent != null) {
                String studentName = deletedStudent.getName();
                String studentLastName = deletedStudent.getLastName();

                // Loguea el estudiante antes de eliminarlo
                System.out.println("Estudiante a eliminar: " + studentName + " " + studentLastName);

                studentService.deleteById(studentId);

                // Loguea después de eliminar
                System.out.println("Estudiante eliminado con ID: " + id);

                return "El estudiante " + studentName + " " + studentLastName + " con ID " + id + " ha sido eliminado.";
            } else {
                // Loguea el caso donde no se encuentra el estudiante
                System.out.println("No se encontró un estudiante con el ID " + id + ". No se realizó ninguna eliminación.");

                return "No se encontró un estudiante con el ID " + id + ". No se realizó ninguna eliminación.";
            }
        } catch (Exception e) {
            // Loguea o imprime la excepción para obtener más detalles
            e.printStackTrace();

            // Puedes incluir el mensaje de error en el retorno
            return "Error al intentar eliminar el estudiante con ID " + id + ". Detalles: " + e.getMessage();
        }
    }


    
    @MutationMapping(name = "updateStudentById")
    public Student updateStudent(@Argument(name = "inputStudent") InputStudent inputStudent) {
        // Obtén el ID del estudiante que deseas actualizar
        Long studentId = inputStudent.getId();

        // Verifica si el estudiante existe antes de intentar actualizarlo
        Student existingStudent = studentService.findById(studentId);

        if (existingStudent != null) {
            // Obtén el curso correspondiente si es necesario
            Course course = courseService.findById(Long.parseLong(inputStudent.getCourseId()));

            // Crea un objeto Student con los datos actualizados
            Student updatedStudent = new Student();
            updatedStudent.setId(studentId);
            
            // Actualiza solo los campos que no sean el ID
            updatedStudent.setName(inputStudent.getName());
            updatedStudent.setLastName(inputStudent.getLastName());
            updatedStudent.setAge(inputStudent.getAge());
            updatedStudent.setCourse(course);

            // Actualiza el estudiante
            studentService.updateStudent(updatedStudent);

            return updatedStudent;
        } else {
            // Manejar el caso en que el estudiante no existe

            System.out.println("No se encontró un estudiante con el ID " + studentId + ". No se realizó ninguna actualización.");
            return null;
       
        }
    }




    
   }
