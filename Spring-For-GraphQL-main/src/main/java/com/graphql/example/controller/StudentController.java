package com.graphql.example.controller;
import com.graphql.example.entities.Course;
import com.graphql.example.entities.Student;
import com.graphql.example.entities.StudentDTO;
import com.graphql.example.graphql.InputStudent;
import com.graphql.example.service.ICourseService;
import com.graphql.example.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
	@Autowired
    private IStudentService studentService;

    @Autowired
    private ICourseService courseService;

    @GetMapping("/students/getById/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable String studentId) {
        Long id = Long.parseLong(studentId);
        Student student = studentService.findById(id);

        if (student != null) {
            StudentDTO studentDTO = convertToDTO(student);
            return ResponseEntity.ok(studentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/students/getAll")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAll();
        
        if (!students.isEmpty()) {
            return ResponseEntity.ok(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/students/createStudent")
    public Student createStudent(@RequestBody InputStudent inputStudent) {
        Course course = courseService.findById(Long.parseLong(inputStudent.getCourseId()));

        Student student = new Student();
        student.setName(inputStudent.getName());
        student.setLastName(inputStudent.getLastName());
        student.setAge(inputStudent.getAge());
        student.setCourse(course);

        studentService.createStudent(student);

        return student;
    }
    
    @PutMapping("/students/updateStudentById/{studentId}")
    public ResponseEntity<Student> updateStudentByIdPUT(@PathVariable String studentId, @RequestBody InputStudent inputStudent) {
        Long id = Long.parseLong(studentId);

        // Busca el estudiante existente
        Student existingStudent = studentService.findById(id);

        if (existingStudent != null) {
            // Actualiza todos los campos con los valores proporcionados en el cuerpo de la solicitud
            existingStudent.setName(inputStudent.getName());
            existingStudent.setLastName(inputStudent.getLastName());
            existingStudent.setAge(inputStudent.getAge());

            // Actualiza el curso si es necesario
            if (inputStudent.getCourseId() != null) {
                Course course = courseService.findById(Long.parseLong(inputStudent.getCourseId()));
                existingStudent.setCourse(course);
            }

            // Guarda los cambios en el estudiante
            studentService.updateStudent(existingStudent);

            // Devuelve el estudiante actualizado
            return ResponseEntity.ok(existingStudent);
        } else {
            // Si no se encuentra el estudiante, devuelve un código 404
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/students/updateStudentById/{studentId}")
    public ResponseEntity<Student> updateStudentByIdPATCH(@PathVariable String studentId, @RequestBody InputStudent inputStudent) {
        Long id = Long.parseLong(studentId);

        // Busca el estudiante existente
        Student existingStudent = studentService.findById(id);

        if (existingStudent != null) {
            // Actualiza los campos proporcionados en el cuerpo de la solicitud
            if (inputStudent.getName() != null) {
                existingStudent.setName(inputStudent.getName());
            }
            if (inputStudent.getLastName() != null) {
                existingStudent.setLastName(inputStudent.getLastName());
            }
            if (inputStudent.getAge() != null) {
                existingStudent.setAge(inputStudent.getAge());
            }
            if (inputStudent.getCourseId() != null) {
                Course course = courseService.findById(Long.parseLong(inputStudent.getCourseId()));
                existingStudent.setCourse(course);
            }

            // Guarda los cambios en el estudiante
            studentService.updateStudent(existingStudent);

            // Devuelve el estudiante actualizado
            return ResponseEntity.ok(existingStudent);
        } else {
            // Si no se encuentra el estudiante, devuelve un código 404
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/students/deleteStudent/{studentId}")
    public String deleteStudentById(@PathVariable String studentId) {
        Long id = Long.parseLong(studentId);
        studentService.deleteById(id);
        return "El estudiante con id " + id + " ha sido eliminado.";
    }

    // Método para convertir Student a StudentDTO
    private StudentDTO convertToDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getLastName(),
                student.getAge(),
                (student.getCourse() != null) ? student.getCourse().getId() : null
        );
    }
}
