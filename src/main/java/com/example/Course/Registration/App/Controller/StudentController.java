package com.example.Course.Registration.App.Controller;


import com.example.Course.Registration.App.DTOs.StudentDTO;
import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.CourseRequest;
import com.example.Course.Registration.App.Entity.Student;
import com.example.Course.Registration.App.Service.CourseService;
import com.example.Course.Registration.App.Service.StudentService;
import com.example.Course.Registration.App.Util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/students")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping("/{id}")
    public ResponseEntity<?> viewStudentDetails(@PathVariable Long id, @RequestParam String token) {
        String username = jwtUtil.extractUsername(token);
        Student existingStudent = studentService.getStudentDetails(id);

        if (existingStudent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        // Check if the extracted username matches the student's username
        if (!existingStudent.getEmail().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to update this student");
        }
        return ResponseEntity.ok(studentService.getStudentDetails(id));
    }

    @PutMapping("/update-student/{id}")
    public ResponseEntity<?> updateStudentDetails(@PathVariable Long id, @RequestBody @Valid StudentDTO studentDTO, @RequestParam String token) {
        String username = jwtUtil.extractUsername(token);
        System.out.print("Fetched username is " + username);
        Student existingStudent = studentService.getStudentDetails(id);

        if (existingStudent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        // Check if the extracted username matches the student's username
        if (!existingStudent.getEmail().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to update this student");
        }

        Student student = studentService.mapToEntity(studentDTO);
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @GetMapping("/get-course-list")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }


    @PostMapping("submit-course-form")
    public String submitCourseRequest(@RequestBody CourseRequest courseRequest, @RequestParam String token) {

        String username = jwtUtil.extractUsername(token);
        Student existingStudent = studentService.getStudentDetails(courseRequest.getStudentId());

        if (existingStudent == null) {
            return "Student not found";
        }

        // Check if the extracted username matches the student's username
        if (!existingStudent.getEmail().equals(username)) {
            return "Unauthorized to update this student";
        }
        return studentService.submitCourseRequest(courseRequest.getStudentId(), courseRequest.getCourseIds(),courseRequest.getTimestamp());
    }


}
