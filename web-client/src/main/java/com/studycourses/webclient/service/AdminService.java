package com.studycourses.webclient.service;

import com.studycourses.webclient.model.AcademicDiscipline;
import com.studycourses.webclient.model.School;
import com.studycourses.webclient.model.StudyGroup;

import java.util.List;

public interface AdminService {

    List<AcademicDiscipline> getListAcademicDiscipline();

    List<School> getListSchools();

    AcademicDiscipline getAcademicDisciplineById(Long id_discipline);

    School getSchoolById(Long id_school);

    void saveAcademicDiscipline(AcademicDiscipline academicDiscipline);

    void saveSchool(School school);

    void dropAcademicDiscipline(Long id_discipline);

    List<StudyGroup> getAllStudyGroups();

    void dropGroupById(Long id_group);

}
