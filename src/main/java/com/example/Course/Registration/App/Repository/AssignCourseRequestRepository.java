package com.example.Course.Registration.App.Repository;

import com.example.Course.Registration.App.Util.AssignCourseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignCourseRequestRepository extends JpaRepository<AssignCourseRequest,Long> {
}
