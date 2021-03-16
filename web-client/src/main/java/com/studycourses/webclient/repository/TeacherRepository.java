package com.studycourses.webclient.repository;

import com.studycourses.webclient.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    Teacher findByLogin(String login);

    boolean existsByLogin(String login);
}
