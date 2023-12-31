package com.team.tesbro.teacher;

import com.team.tesbro.academy.Academy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Page<Teacher> findAllByAcademy(Academy academy, Pageable pageable);
    List<Teacher> findByAcademyId(Integer academyId);
}
