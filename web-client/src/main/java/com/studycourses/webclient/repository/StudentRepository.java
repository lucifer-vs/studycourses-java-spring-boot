package com.studycourses.webclient.repository;

import com.studycourses.webclient.model.Student;
import com.studycourses.webclient.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Student findByLogin(String login);

    List<Student> findByStudyGroup(StudyGroup studyGroup);

    void deleteById(Long id_student);

    boolean existsByStudyGroup(StudyGroup studyGroup);
}
