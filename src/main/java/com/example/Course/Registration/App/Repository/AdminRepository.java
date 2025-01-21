package com.example.Course.Registration.App.Repository;

import com.example.Course.Registration.App.Entity.Admin;
import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    Optional<Admin> findByUsername(String username);
    Optional<Admin> findByEmail(String email);

}
