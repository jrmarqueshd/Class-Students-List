package com.junior.courses;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepository extends JpaRepository<CourseEntity, Long> {
}
