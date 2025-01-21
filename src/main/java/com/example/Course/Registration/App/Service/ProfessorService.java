package com.example.Course.Registration.App.Service;


import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Professor;
import com.example.Course.Registration.App.Repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public Professor getProfessorDetails(Long id){
        return professorRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Professor not found"));
    }

       public Professor updateProfessorDetails(Long id, Professor updatedDetails) {
        Professor professor = getProfessorDetails(id);
        professor.setName(updatedDetails.getName());
        professor.setEmail(updatedDetails.getEmail());
        professor.setPassword(updatedDetails.getPassword());
        return professorRepository.save(professor);
    }

    public List<Course> getAssignedCourses(Long professorId) {
        Professor professor = getProfessorDetails(professorId);
        return professor.getCourses();
    }


}
