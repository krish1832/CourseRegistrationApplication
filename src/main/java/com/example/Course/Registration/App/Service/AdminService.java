package com.example.Course.Registration.App.Service;

import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Professor;
import com.example.Course.Registration.App.Entity.Student;
import com.example.Course.Registration.App.Repository.*;
import com.example.Course.Registration.App.Entity.CourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Comparator;
import java.util.List;

@Service
@Validated

public class AdminService {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseRequestRepository courseRequestRepository;



    public Student addStudent(Student newStudent) {
        return studentRepository.save(newStudent);
    }

    public Professor addProfessor(Professor newProfessor) {
        return professorRepository.save(newProfessor);
    }

    public Course addCourse(Course newCourse) {
        return courseRepository.save(newCourse);
    }




    public String assignCoursesFCFS() {


        List<CourseRequest> requests = courseRequestRepository.findAll();
        requests.sort(Comparator.comparing(CourseRequest::getTimestamp));

        StringBuilder notAssigned = new StringBuilder();
        StringBuilder assigned = new StringBuilder();

        for (CourseRequest request : requests) {
            Student student = studentRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            List<Course> courses = courseRepository.findAllById(request.getCourseIds());

            for (Course course : courses) {
                if (course.getRemainingSeats() > 0) {
                    // Assign the course to the student
                    course.getStudents().add(student);
                    student.getCourses().add(course);
                    assigned.append("Course ID ").append(course.getId())
                            .append(" (").append(course.getCourseName()).append(") ")
                            .append("has been successfully assigned.\n");
                } else {
                    notAssigned.append("Course ID ").append(course.getId())
                            .append(" (").append(course.getCourseName()).append(") ")
                            .append("is full. Could not assign to Student ID ")
                            .append(student.getId()).append(".\n");
                }
            }
            // Save the updated student entity
            studentRepository.save(student);

            // Send notification to the student via email
            sendNotification(student.getEmail(), assigned.toString(), notAssigned.toString());

            // Clear assigned and notAssigned for the next student
            assigned.setLength(0);
            notAssigned.setLength(0);
        }

        // Clear all course requests after processing
        courseRequestRepository.deleteAll();

        return notAssigned.length() > 0 ? notAssigned.toString() : "All courses assigned successfully.";
    }



    private void sendNotification(String studentEmail, String assignedCourses, String notAssignedCourses) {
        String subject = "Your Course Assignment Status";
        StringBuilder message = new StringBuilder();

        message.append("Dear Student,\n\n")
                .append("Here are your course assignment results:\n\n")
                .append(assignedCourses)
                .append("\n")
                .append(notAssignedCourses)
                .append("\nBest regards,\nYour Course Management Team");

        // Send email notification
        emailService.sendEmail(studentEmail, subject, message.toString());
    }














}
