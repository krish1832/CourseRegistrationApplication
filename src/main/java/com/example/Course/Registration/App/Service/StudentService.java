package com.example.Course.Registration.App.Service;

import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Student;
import com.example.Course.Registration.App.Repository.AssignCourseRequestRepository;
import com.example.Course.Registration.App.Repository.CourseRepository;
import com.example.Course.Registration.App.Repository.StudentRepository;
import com.example.Course.Registration.App.Util.AssignCourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AssignCourseRequestRepository assignCourseRequestRepository;

    public Student getStudentDetails(Long id){
        return studentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Student Not Found!"));
    }

    public Student updateStudentDetails(Long id,Student updatedStudent){
        Student student = getStudentDetails(id);
        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setPassword(updatedStudent.getPassword());
        return studentRepository.save(student);
    }
    public List<Course> getAllCourses(Long studentId) {
        return courseRepository.findAll(); // Retrieve all courses
    }

    public String submitCourseRequest(Long studentId, List<Long> courseIds) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        AssignCourseRequest request = new AssignCourseRequest();
        request.setStudentId(studentId);
        request.setCourseIds(courseIds);
        assignCourseRequestRepository.save(request);

        return "Course request saved. Admin will process it shortly.";
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void registerStudent(Student student) {
        studentRepository.save(student);
    }

    public Optional<Student> findStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}
