package com.graphql.example.controller;
import com.graphql.example.entities.Course;
import com.graphql.example.entities.Student;
import com.graphql.example.entities.StudentDTO;
import com.graphql.example.graphql.InputStudent;
import com.graphql.example.service.ICourseService;
import com.graphql.example.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // Obtener estudiante por ID
    @GetMapping("/students/getById/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable String studentId) {
        try {
            // Convertir el ID de estudiante a Long
            Long id = Long.parseLong(studentId);
            // Buscar estudiante por ID
            Student student = studentService.findById(id);

            if (student != null) {
                // Convertir estudiante a DTO y devolver en respuesta exitosa
                StudentDTO studentDTO = convertToDTO(student);
                return ResponseEntity.ok(studentDTO);
            } else {
                // Devolver respuesta 404 si el estudiante no se encuentra
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            // Devolver respuesta 400 si hay un error en el formato del ID
            return ResponseEntity.badRequest().body("Error: El formato del ID del estudiante es incorrecto.");
        } catch (Exception e) {
            // Devolver respuesta 500 en caso de error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    // Obtener todos los estudiantes
    @GetMapping("/students/getAll")
    public ResponseEntity<?> getAllStudents() {
        try {
            // Obtener todos los estudiantes
            List<Student> students = studentService.findAll();

            if (!students.isEmpty()) {
                // Devolver lista de estudiantes en respuesta exitosa
                return ResponseEntity.ok(students);
            } else {
                // Devolver respuesta 404 si no hay estudiantes
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Devolver respuesta 500 en caso de error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    // Crear un nuevo estudiante
    @PostMapping("/students/createStudent")
    public ResponseEntity<?> createStudent(@RequestBody InputStudent inputStudent) {
        try {
            // Obtener el curso por ID
            Course course = courseService.findById(Long.parseLong(inputStudent.getCourseId()));

            // Crear un nuevo estudiante
            Student student = new Student();
            student.setName(inputStudent.getName());
            student.setLastName(inputStudent.getLastName());
            student.setAge(inputStudent.getAge());
            student.setCourse(course);

            // Guardar el estudiante en la base de datos
            studentService.createStudent(student);

            // Devolver el estudiante creado en respuesta exitosa
            return ResponseEntity.ok(student);
        } catch (NumberFormatException e) {
            // Devolver respuesta 400 si hay un error en el formato del ID del curso
            return ResponseEntity.badRequest().body("Error: El formato del ID del curso es incorrecto.");
        } catch (Exception e) {
            // Devolver respuesta 500 en caso de error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    // Actualizar estudiante por ID (método PUT)
    @PutMapping("/students/updateStudentById/{studentId}")
    public ResponseEntity<?> updateStudentByIdPUT(@PathVariable String studentId, @RequestBody InputStudent inputStudent) {
        try {
            // Convertir el ID de estudiante a Long
            Long id = Long.parseLong(studentId);
            // Buscar estudiante existente por ID
            Student existingStudent = studentService.findById(id);

            if (existingStudent != null) {
                // Actualizar los campos con los valores proporcionados
                existingStudent.setName(inputStudent.getName());
                existingStudent.setLastName(inputStudent.getLastName());
                existingStudent.setAge(inputStudent.getAge());

                // Actualizar el curso si es necesario
                if (inputStudent.getCourseId() != null) {
                    Course course = courseService.findById(Long.parseLong(inputStudent.getCourseId()));
                    existingStudent.setCourse(course);
                }

                // Guardar los cambios en el estudiante
                studentService.updateStudent(existingStudent);

                // Devolver el estudiante actualizado en respuesta exitosa
                return ResponseEntity.ok(existingStudent);
            } else {
                // Devolver respuesta 404 si no se encuentra el estudiante
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            // Devolver respuesta 400 si hay un error en el formato del ID del estudiante
            return ResponseEntity.badRequest().body("Error: El formato del ID del estudiante es incorrecto.");
        } catch (Exception e) {
            // Devolver respuesta 500 en caso de error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    // Actualizar estudiante por ID (método PATCH)
    @PatchMapping("/students/updateStudentById/{studentId}")
    public ResponseEntity<?> updateStudentByIdPATCH(@PathVariable String studentId, @RequestBody InputStudent inputStudent) {
        try {
            // Convertir el ID de estudiante a Long
            Long id = Long.parseLong(studentId);
            // Buscar estudiante existente por ID
            Student existingStudent = studentService.findById(id);

            if (existingStudent != null) {
                // Actualizar los campos proporcionados en el cuerpo de la solicitud
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

                // Guardar los cambios en el estudiante
                studentService.updateStudent(existingStudent);

                // Devolver el estudiante actualizado en respuesta exitosa
                return ResponseEntity.ok(existingStudent);
            } else {
                // Devolver respuesta 404 si no se encuentra el estudiante
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            // Devolver respuesta 400 si hay un error en el formato del ID del estudiante
            return ResponseEntity.badRequest().body("Error: El formato del ID del estudiante es incorrecto.");
        } catch (Exception e) {
            // Devolver respuesta 500 en caso de error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    // Eliminar estudiante por ID
    @DeleteMapping("/students/deleteStudent/{studentId}")
    public ResponseEntity<?> deleteStudentById(@PathVariable String studentId) {
        try {
            // Convertir el ID de estudiante a Long
            Long id = Long.parseLong(studentId);
            
            // Verificar si el estudiante existe antes de intentar eliminarlo
            Student existingStudent = studentService.findById(id);
            
            if (existingStudent != null) {
                // Eliminar estudiante por ID
                studentService.deleteById(id);
                return ResponseEntity.ok("El estudiante con ID " + id + " ha sido eliminado.");
            } else {
                // Devolver respuesta 404 si el estudiante no existe
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            // Devolver respuesta 400 si hay un error en el formato del ID del estudiante
            return ResponseEntity.badRequest().body("Error: El formato del ID del estudiante es incorrecto.");
        } catch (Exception e) {
            // Devolver respuesta 500 en caso de error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
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
