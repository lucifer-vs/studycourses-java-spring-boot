package com.studycourses.webclient.repository;

import com.studycourses.webclient.model.Course;
import com.studycourses.webclient.model.GroupCourses;
import com.studycourses.webclient.model.School;
import com.studycourses.webclient.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup,Long> {


    StudyGroup getStudyGroupByNameAndAndSchool(String name, School school);


    boolean existsStudyGroupByNameAndSchool(String name, School school);

}
