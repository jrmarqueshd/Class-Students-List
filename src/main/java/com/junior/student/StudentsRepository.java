package com.junior.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentsRepository extends JpaRepository<StudentEntity, Long>{
    boolean existsByEmail(String email);
}
