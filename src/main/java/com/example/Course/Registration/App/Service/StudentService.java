package com.example.Course.Registration.App.Service;

import com.example.Course.Registration.App.DTOs.StudentDTO;
import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Student;
import com.example.Course.Registration.App.Repository.CourseRepository;
import com.example.Course.Registration.App.Repository.CourseRequestRepository;
import com.example.Course.Registration.App.Repository.StudentRepository;
import com.example.Course.Registration.App.Entity.CourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseRequestRepository courseRequestRepository;

    public Student getStudentDetails(Long id){
        return studentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Student Not Found!"));
    }

    public void deleteStudent(Long id){
        if(studentRepository.existsById(id)){
            studentRepository.deleteById(id);
        }else {
            throw new IllegalArgumentException("Student with ID "+id+" is not Found");
        }
    }

    public Student addStudent(Student s){
        return studentRepository.save(s);
    }

    public Student updateStudent(Long id,Student updatedStudent){
        Student student = getStudentDetails(id);
        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        String encodedPassword = passwordEncoder.encode(updatedStudent.getPassword());
        student.setPassword(encodedPassword);
        return studentRepository.save(student);
    }
    public List<Course> getAllCourses(Long studentId) {
        return courseRepository.findAll(); // Retrieve all courses
    }

    public String submitCourseRequest(Long studentId, List<Long> courseIds, LocalDateTime time) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        CourseRequest request = new CourseRequest();
        request.setStudentId(studentId);
        request.setCourseIds(courseIds);
        request.setTimestamp(time);
        courseRequestRepository.save(request);

        return "Course request saved. Admin will process it shortly.";
    }

    public Student mapToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        String encodedPassword = passwordEncoder.encode(studentDTO.getPassword());
        student.setPassword(encodedPassword); // Set the encoded password
        return student;
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
