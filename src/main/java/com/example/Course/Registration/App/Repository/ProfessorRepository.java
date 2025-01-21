package com.example.Course.Registration.App.Repository;

import com.example.Course.Registration.App.Entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Spliterator;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
//    Optional<Professor> findByEmail(String email);
    Optional<Professor> findById(Long id);
    Optional<Professor> findByEmail(String email);


}