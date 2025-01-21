package com.example.Course.Registration.App.Controller;

import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Professor;
import com.example.Course.Registration.App.Entity.Semester;
import com.example.Course.Registration.App.Entity.Student;
import com.example.Course.Registration.App.Service.AdminService;
import com.example.Course.Registration.App.Util.AssignCourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/students")
    public ResponseEntity<Student> addStudent(@RequestBody Student newStudent) {
        return ResponseEntity.ok(adminService.addStudent(newStudent));
    }

    @PostMapping("/professors")
    public ResponseEntity<Professor> addProfessor(@RequestBody Professor newProfessor) {
        return ResponseEntity.ok(adminService.addProfessor(newProfessor));
    }


    @PostMapping("/courses")
    public ResponseEntity<Course> addCourse(@RequestBody Course newCourse) {
        return ResponseEntity.ok(adminService.addCourse(newCourse));
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourse(@RequestBody Course newCourse,@PathVariable Long Id){
        return ResponseEntity.ok(adminService.updateCourse(Id,newCourse));
    }
    @PutMapping("/semesters/{id}")
    public ResponseEntity<?> updateSemester(@RequestBody Semester newSem, @PathVariable Long Id){
        return ResponseEntity.ok(adminService.updateSemester(Id,newSem));
    }

    @PostMapping("/assign-course")
    public ResponseEntity<String> assignCoursesFCFS() {
        return ResponseEntity.ok(adminService.assignCoursesFCFS());
    }
}