package com.junior.enrollments;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentsRepository extends JpaRepository<EnrollmentEntity, Long> {
}
