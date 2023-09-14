package com.example.demo.controller;

import com.example.demo.Exception.BadRequestException;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    //    @GetMapping("/{id}")
//    public Student getStudentById(@PathVariable Long id) {
//        return studentService.getStudentById(id);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            throw new ResourceNotFoundException("Student with ID " + id + " not found");
        }
        return ResponseEntity.ok(student);
    }


    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        if (student == null || student.getUsername() == null || student.getPassword() == null) {
            throw new BadRequestException("Invalid student data");
        }

        if (student.getUsername().length() < 5 || student.getPassword().length() < 3) {
            throw new BadRequestException("Username must be at least 5 characters long, and password must be at least 8 characters long.");
        }
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);

        // return studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentService.updateStudent(id, updatedStudent);
    }


//    @DeleteMapping("/{id}")
//    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
//       Student student= studentService.getStudentById(id);
//       if(student==null){
//           throw new ResourceNotFoundException("cannot delete with invalid student data");
//       }
//       studentService.deleteStudent(id);
//        return ResponseEntity.ok("Student with ID " + id + " has been deleted");
//      //  return ResponseEntity.ok().build();
//    }
@DeleteMapping("/{id}")
public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
    // Check if the student with the given ID exists
    Student existingStudent = studentService.getStudentById(id);
    if (existingStudent == null) {
        throw new ResourceNotFoundException("Student with ID " + id + " not found");
    }

    // Perform additional validation checks here if needed

    // If all validation checks pass, proceed with deletion
    studentService.deleteStudent(id);

    return ResponseEntity.ok("Student with ID " + id + " has been deleted");
}

//if(student.getUsername()==null || student.getId()==null ){
//      throw new BadRequestException("cannot delete with invalid student data");
//}
//else
//    return studentService.deleteStudent(student);
//}


//    @PatchMapping("/{id}")
//    public ResponseEntity<Student> patchStudent(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
//        Student existingStudent = studentService.getStudentById(id);
//
//        if (existingStudent == null) {
//            return ResponseEntity.notFound().build();
//        }
//        studentService.applyPartialUpdates();
//
//
//        Student updatedStudent = studentService.updateStudent(id, existingStudent);
//
//        if (updatedStudent != null) {
//            return ResponseEntity.ok(updatedStudent);
//        } else {
//            return ResponseEntity.badRequest().body("Invalid update");
//        }
//    }

}