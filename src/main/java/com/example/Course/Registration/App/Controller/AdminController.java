package com.example.Course.Registration.App.Controller;

import com.example.Course.Registration.App.DTOs.CourseDTO;
import com.example.Course.Registration.App.DTOs.ProfessorDTO;
import com.example.Course.Registration.App.DTOs.SemesterDTO;
import com.example.Course.Registration.App.DTOs.StudentDTO;
import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Professor;
import com.example.Course.Registration.App.Entity.Semester;
import com.example.Course.Registration.App.Entity.Student;
import com.example.Course.Registration.App.Service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/add-students")
    public ResponseEntity<Student> addStudent(@Valid @RequestBody StudentDTO studentDTO) {
        // Convert DTO to entity in the service layer or manually here
        Student student = studentService.mapToEntity(studentDTO);
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    @PostMapping("/add-professors")
    public ResponseEntity<Professor> addProfessor(@Valid @RequestBody ProfessorDTO professorDTO) {
        Professor professor = professorService.mapToEntity(professorDTO);
        return ResponseEntity.ok(professorService.addProfessor(professor));
    }

    @PostMapping("/add-courses")
    public ResponseEntity<Course> addCourse(@Valid @RequestBody CourseDTO courseDTO) {
        Course course = courseService.mapToEntity(courseDTO);
        return ResponseEntity.ok(courseService.addCourse(course));
    }

    @PostMapping("/add-semester")
    public ResponseEntity<Semester> addSemester(@Valid @RequestBody SemesterDTO semesterDTO) {
        Semester semester = semesterService.mapToEntity(semesterDTO);
        return ResponseEntity.ok(semesterService.addSemester(semester));
    }

    @PutMapping("/update-semester/{id}")
    public ResponseEntity<Semester> updateSemester(@Valid @RequestBody SemesterDTO semesterDTO, @PathVariable Long id) {
        Semester semester = semesterService.mapToEntity(semesterDTO);
        return ResponseEntity.ok(semesterService.updateSemester(id, semester));
    }

    @PutMapping("/update-courses/{id}")
    public ResponseEntity<Course> updateCourse(@Valid @RequestBody CourseDTO courseDTO, @PathVariable Long id) {
        Course course = courseService.mapToEntity(courseDTO);
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    @DeleteMapping("/delete-student/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @DeleteMapping("/delete-professor/{id}")
    public void deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
    }

    @DeleteMapping("/delete-course/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/assign-course-send-mail-notification")
    public ResponseEntity<String> assignCoursesFCFS() {
        return ResponseEntity.ok(adminService.assignCoursesFCFS());
    }


    @GetMapping("/send-mail-notification")
    public ResponseEntity<String> sendMailNotification() {
        // Retrieve all students from the database
        List<Student> students = studentService.getAllStudents();

        // Prepare a generic notification message
        String subject = "Course Registration Notification";
        String message = "Dear Student,\n\n"
                + "We are excited to inform you that the course registration process has begun. "
                + "Please visit the registration portal to complete your course selection at your earliest convenience.\n\n"
                + "Best regards,\n"
                + "Course Registration Team";

        // Send email to each student
        for (Student student : students) {
            emailService.sendEmail(student.getEmail(), subject, message);
        }

        return ResponseEntity.ok("Registration notifications sent to all students successfully.");
    }

}
