package com.example.Course.Registration.App.Service;


import com.example.Course.Registration.App.DTOs.ProfessorDTO;
import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Professor;
import com.example.Course.Registration.App.Repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Professor getProfessorById(Long id){
        return professorRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Professor not found"));
    }

    public Professor addProfessor(Professor newProfessor){
        return professorRepository.save(newProfessor);
    }

       public Professor updateProfessorDetails(Long id, Professor updatedDetails) {
        Professor professor = getProfessorById(id);
        professor.setName(updatedDetails.getName());
        professor.setEmail(updatedDetails.getEmail());
           String encodedPassword = passwordEncoder.encode(professor.getPassword());
           professor.setPassword(encodedPassword);
           return professorRepository.save(professor);
    }

    public List<Course> getAssignedCourses(Long professorId) {
        Professor professor = getProfessorById(professorId);
        return professor.getCourses();
    }

    public void deleteProfessor(Long id){
        if(professorRepository.existsById(id)){
            professorRepository.deleteById(id);
        }else {
            throw new IllegalArgumentException("Student with ID "+id+" is not Found");
        }
    }


    public Professor mapToEntity(ProfessorDTO professorDTO) {
        Professor professor = new Professor();
        professor.setName(professorDTO.getName());
        professor.setEmail(professorDTO.getEmail());
        String encodedPassword = passwordEncoder.encode(professor.getPassword());
        professor.setPassword(encodedPassword);
        // Any additional fields can be mapped here
        return professor;
    }






}
