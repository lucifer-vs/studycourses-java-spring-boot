package com.studycourses.webclient.repository;

import com.studycourses.webclient.model.Course;
import com.studycourses.webclient.model.GroupCourses;
import com.studycourses.webclient.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupCoursesRepository extends JpaRepository<GroupCourses,Long> {

    List<GroupCourses> findByStudyGroup(StudyGroup studyGroup);

    List<GroupCourses> findByCourse(Course course);

    void deleteByCourseAndStudyGroup(Course course,StudyGroup studyGroup);

    void deleteAllByCourse(Course course);

    GroupCourses findByCourseAndStudyGroup(Course course, StudyGroup studyGroup);

}
