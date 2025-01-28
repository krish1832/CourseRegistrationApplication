package com.example.Course.Registration.App.Service;

import com.example.Course.Registration.App.DTOs.CourseDTO;
import com.example.Course.Registration.App.DTOs.SemesterDTO;
import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Semester;
import com.example.Course.Registration.App.Repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEventHttpMessageWriter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SemesterService {




    @Autowired
    private SemesterRepository semesterRepository;

    public Semester updateSemester(Long id,Semester updatedSemester) {
        Optional<Semester> optionalSemester = semesterRepository.findById(id);

        if (optionalSemester.isPresent()) {
            Semester existingSemester = optionalSemester.get();
            existingSemester.setName(updatedSemester.getName());
            existingSemester.setStartDate(updatedSemester.getStartDate());
            existingSemester.setEndDate(updatedSemester.getEndDate());
            existingSemester.setCourses(updatedSemester.getCourses());
            existingSemester.setStudents(updatedSemester.getStudents());

            return semesterRepository.save(existingSemester);


        } else {
            return null;
        }
    }


    public Semester addSemester(Semester newSemester) {
        return semesterRepository.save(newSemester);
    }

    public Semester mapToEntity(SemesterDTO semesterDTO) {

        Semester newSemester = new Semester();
        newSemester.setName(semesterDTO.getSemesterName());
        newSemester.setStartDate(semesterDTO.getStartDate());
        newSemester.setEndDate(semesterDTO.getEndDate());



        // Any additional fields can be mapped here
        return newSemester;
    }


    public Semester getSemesterByName(String name){
        return semesterRepository.findByName(name).orElseThrow(() -> new RuntimeException("Semester not found with id: " + name));
    }

}
