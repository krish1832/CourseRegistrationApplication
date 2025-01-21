package com.example.Course.Registration.App.Repository;

import com.example.Course.Registration.App.Entity.Course;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

}
