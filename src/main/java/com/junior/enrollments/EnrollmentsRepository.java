package com.junior.enrollments;

import com.junior.courses.CourseEntity;
import com.junior.student.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

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
    List<StudentEntity> findCourseStudentsById(Long course_id);

    @Query("""
        SELECT COUNT(e) > 0
        FROM EnrollmentEntity e
        WHERE e.student.id = :student_id
    """)
    boolean isStudentEnrolled (Long student_id);

    Optional<EnrollmentEntity> findByStudentIdAndCourseId(Long studentId, Long courseId);

    boolean existsByCourseId(Long courseId);

    void deleteByStudentId (Long courseId);

    void deleteByCourseId (Long courseId);
}
