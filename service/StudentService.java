package com.example.demo.service;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        if (studentRepository.existsById(id)) {
            updatedStudent.setId(id);
            return studentRepository.save(updatedStudent);
        } else {
            return null;
        }
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }


//   public void applyPartialUpdates(Student student, Map<String, Object> updates) {
//
//
//        for (Map.Entry<String, Object> entry : updates.entrySet()) {
//            String fieldName = entry.getKey();
//            Object updatedValue = entry.getValue();
//
//
//            if ("username".equals(fieldName)) {
//                student.setUsername((String) updatedValue);
//            } else if ("password".equals(fieldName)) {
//                student.setPassword((String) updatedValue);
//            }
//
//        }
//    }
}
