package com.example.Course.Registration.App.Controller;


import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Student;
import com.example.Course.Registration.App.Repository.AssignCourseRequestRepository;
import com.example.Course.Registration.App.Repository.StudentRepository;
import com.example.Course.Registration.App.Service.StudentService;
import com.example.Course.Registration.App.Util.AssignCourseRequest;
import jakarta.persistence.GeneratedValue;
import org.aspectj.lang.annotation.DeclareWarning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/students")
//@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    @Autowired
    private StudentService studentService;



    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> viewStudentDetails(@PathVariable Long id){
        return  ResponseEntity.ok(studentService.getStudentDetails(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudentDetails(@PathVariable Long id,@RequestBody Student updateDetails){
        return ResponseEntity.ok(studentService.updateStudentDetails(id,updateDetails));
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<List<Course>> viewAllCourses(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getAllCourses(id));
    }



    @PostMapping("/{id}/request-course")
    public String submitCourseRequest(Long studentId, List<Long> courseIds) {
          return studentService.submitCourseRequest(studentId,courseIds);
    }




}
