package com.example.Course.Registration.App.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRequestRepository extends JpaRepository<com.example.Course.Registration.App.Entity.CourseRequest,Long> {
}
