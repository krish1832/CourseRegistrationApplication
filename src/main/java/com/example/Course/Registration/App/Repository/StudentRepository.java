package com.example.Course.Registration.App.Repository;

import com.example.Course.Registration.App.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Student> findByName(String username);
    Optional<Student> findByEmail(String email);


}
