package com.example.Course.Registration.App.Repository;

import com.example.Course.Registration.App.Entity.Semester;
import com.example.Course.Registration.App.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester,Long> {
}
