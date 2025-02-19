package com.example.Course.Registration.App.Service;

import com.example.Course.Registration.App.Entity.Admin;
import com.example.Course.Registration.App.Entity.Professor;
import com.example.Course.Registration.App.Entity.Student;
import com.example.Course.Registration.App.Repository.AdminRepository;
import com.example.Course.Registration.App.Repository.ProfessorRepository;
import com.example.Course.Registration.App.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {


        Optional<Admin> adminEntityOptional = adminRepository.findByEmail(username);
        if (adminEntityOptional.isPresent()) {
            Admin admin = adminEntityOptional.get();
            return User.builder()
                    .username(admin.getEmail())
                    .password(admin.getPassword())  // we have to ensure that password is hashed in DB
                    .roles("ADMIN") // we can assign role here
                    .build();
        }

        // Checking for Student
        Optional<Student> studentEntityOptional = studentRepository.findByEmail(username);
        if (studentEntityOptional.isPresent()) {
            Student student = studentEntityOptional.get();
            return User.builder()
                    .username(student.getEmail())
                    .password(student.getPassword())  
                    .roles("STUDENT") 
                    .build();
        }

        // Check for Professor
        Optional<Professor> professorEntityOptional = professorRepository.findByEmail(username);
        if (professorEntityOptional.isPresent()) {
            Professor professor = professorEntityOptional.get();
            return User.builder()
                    .username(professor.getEmail())
                    .password(professor.getPassword())  
                    .roles("PROFESSOR") 
                    .build();
        }

        // If no user found then
        throw new RuntimeException("User not found with username: " + username);
    }
}
