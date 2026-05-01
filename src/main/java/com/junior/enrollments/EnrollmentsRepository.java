package com.junior.enrollments;

import com.junior.courses.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnrollmentsRepository extends JpaRepository<EnrollmentEntity, Long> {
    @Query("""
        SELECT e.course
        FROM EnrollmentEntity e
        WHERE e.student.id = :student_id
    """)
    List<CourseEntity> findStudentCoursesById(Long student_id);

    @Query("""
        SELECT e.student
        FROM EnrollmentEntity e
        WHERE e.course.id = :course_id
    """)
    List<CourseEntity> findCourseStudentsById(Long course_id);
}
