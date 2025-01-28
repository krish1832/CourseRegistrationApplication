package com.example.Course.Registration.App.Service;

import com.example.Course.Registration.App.DTOs.CourseDTO;
import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Professor;
import com.example.Course.Registration.App.Entity.Semester;
import com.example.Course.Registration.App.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private SemesterService semesterService;

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course addCourse(Course course) {
      return  courseRepository.save(course);
    }

    public void deleteCourse(Long id){
        if(courseRepository.existsById(id)){
            courseRepository.deleteById(id);
        }else {
            throw new IllegalArgumentException("Student with ID "+id+" is not Found");
        }
    }

    public Course updateCourse(Long id, Course updatedDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setCourseName(updatedDetails.getCourseName());
        course.setCourseCredit(updatedDetails.getCourseCredit());
        return courseRepository.save(course);

    }


    public Course mapToEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setCourseName(courseDTO.getCourseName());
        course.setCapacity(courseDTO.getCapacity());
        course.setRemainingSeats(courseDTO.getRemainingSeats());
        course.setCourseCredit(courseDTO.getCourseCredit());

        // Set the Professor entity by looking it up using professorId
        Professor professor = professorService.getProfessorById(courseDTO.getProfessorId());
        course.setProfessor(professor);

        // Convert Semester using semesterService
        Semester semester = semesterService.getSemesterByName(courseDTO.getSemesterName());
        course.setSemester(semester);

        // Any additional fields can be mapped here
        return course;
    }


}
