package com.example.Course.Registration.App.Controller;


import com.example.Course.Registration.App.DTOs.StudentDTO;
import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.CourseRequest;
import com.example.Course.Registration.App.Entity.Student;
import com.example.Course.Registration.App.Service.CourseService;
import com.example.Course.Registration.App.Service.StudentService;
import com.example.Course.Registration.App.Util.JwtRequest;
import com.example.Course.Registration.App.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/students")
@PreAuthorize("hasRole('STUDENT')") // Ensure this matches the role in the token
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private JwtUtil jwtUtil;




    @GetMapping("/{id}")
    public ResponseEntity<Student> viewStudentDetails(@PathVariable Long id){
        return  ResponseEntity.ok(studentService.getStudentDetails(id));
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/update-student/{id}")
    public ResponseEntity<Student> updateStudentDetails(@PathVariable Long id,@RequestBody StudentDTO studentDTO,@RequestParam String token){

        Student student = studentService.mapToEntity(studentDTO);
        return ResponseEntity.ok(studentService.updateStudent(id,student));
    }
    @GetMapping("/get-course-list")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }


    @PostMapping("submit-course-form")
    public String submitCourseRequest(@RequestBody CourseRequest courseRequest) {
          return studentService.submitCourseRequest(courseRequest.getStudentId(),courseRequest.getCourseIds());
    }




}
