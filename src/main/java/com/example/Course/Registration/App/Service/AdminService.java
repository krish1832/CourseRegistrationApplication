package com.example.Course.Registration.App.Service;

import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Professor;
import com.example.Course.Registration.App.Entity.Semester;
import com.example.Course.Registration.App.Entity.Student;
import com.example.Course.Registration.App.Repository.*;
import com.example.Course.Registration.App.Util.AssignCourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AdminService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AssignCourseRequestRepository assignCourseRequestRepository;
    @Autowired
    private SemesterRepository semesterRepository;


    public Student addStudent(Student newStudent) {
        return studentRepository.save(newStudent);
    }

    public Professor addProfessor(Professor newProfessor) {
        return professorRepository.save(newProfessor);
    }

    public Course addCourse(Course newCourse) {
        return courseRepository.save(newCourse);
    }

    public Semester addSemester(Semester newSemester) {
        return semesterRepository.save(newSemester);
    }


    public String assignCoursesFCFS() {
        List<AssignCourseRequest> requests = assignCourseRequestRepository.findAll();
        StringBuilder result = new StringBuilder();

        for (AssignCourseRequest request : requests) {
            Student student = studentRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            List<Course> courses = courseRepository.findAllById(request.getCourseIds());

            for (int i=0;i<courses.size();i++) {
                  Course course = courses.get(i);
//                  course.setCredits(5);
                if (course.getStudents().size() < course.getCapacity()) {
                    course.getStudents().add(student);
                    student.getCourses().add(course);
                } else {
                    result.append("Course ID ").append(course.getId())
                            .append(" is full. Could not assign to Student ID ")
                            .append(student.getId()).append(".\n");
                }
            }
            studentRepository.save(student);
        }

        assignCourseRequestRepository.deleteAll(); // Clear the requests after processing
        return result.length() > 0 ? result.toString() : "All courses assigned successfully.";
    }




    public Course updateCourse(Long id, Course updatedDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setName(updatedDetails.getName());
        course.setCredits(updatedDetails.getCredits());
        return courseRepository.save(course);

    }


    public Semester updateSemester(Long id, Semester updatedSemester) {

        Semester semester = semesterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        semester.setName(updatedSemester.getName());
        semester.setId(updatedSemester.getId());
        semester.setCourses(updatedSemester.getCourses());
        semester.setStartDate(updatedSemester.getStartDate());
        semester.setEndDate(updatedSemester.getEndDate());
        semester.setStudents(updatedSemester.getStudents());
        return semesterRepository.save(semester);
    }






}
