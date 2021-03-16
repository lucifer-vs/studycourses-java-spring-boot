package com.studycourses.webclient.service;

import com.studycourses.webclient.model.*;

import java.util.List;

public interface CourseService {

    List<Course> getListCourse();

    List<Task> getListTaskByCourse(Course course);

    Course findById(Long id_course);

    boolean existsCourseById(Long id_course);

    void saveCourse(Course course);

    Course getCourseByName(String name);

    List<GroupCourses> getListGroupsByIdCourse(Long id_course);

    List<StudyGroup> getAllStudyGroups();

    void addGroup(Long id_course,StudyGroup studyGroup);

    boolean existsByTeacher(Teacher teacher);

    void dropGroup(Long id_group,Long id_course);

    void dropCourse(Long id_course);

    List<Progress> getProgressByGroupAndCourse(StudyGroup studyGroup,Course course);
}
