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

        // Check for Admin
        Optional<Admin> adminEntityOptional = adminRepository.findByEmail(username);
        if (adminEntityOptional.isPresent()) {
            Admin admin = adminEntityOptional.get();
            return User.builder()
                    .username(admin.getEmail())
                    .password(admin.getPassword())  // Ensure the password is hashed in DB
                    .roles("ADMIN") // You can assign roles here
                    .build();
        }

        // Check for Student
        Optional<Student> studentEntityOptional = studentRepository.findByEmail(username);
        if (studentEntityOptional.isPresent()) {
            Student student = studentEntityOptional.get();
            return User.builder()
                    .username(student.getEmail())
                    .password(student.getPassword())  // Ensure the password is hashed in DB
                    .roles("STUDENT") // You can assign roles here
                    .build();
        }

        // Check for Professor
        Optional<Professor> professorEntityOptional = professorRepository.findByEmail(username);
        if (professorEntityOptional.isPresent()) {
            Professor professor = professorEntityOptional.get();
            return User.builder()
                    .username(professor.getEmail())
                    .password(professor.getPassword())  // Ensure the password is hashed in DB
                    .roles("PROFESSOR") // You can assign roles here
                    .build();
        }

        // If no user found
        throw new RuntimeException("User not found with username: " + username);
    }
}
