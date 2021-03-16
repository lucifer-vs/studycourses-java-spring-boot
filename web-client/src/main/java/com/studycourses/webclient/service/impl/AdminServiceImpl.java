package com.studycourses.webclient.service.impl;

import com.studycourses.webclient.model.AcademicDiscipline;
import com.studycourses.webclient.model.School;
import com.studycourses.webclient.model.StudyGroup;
import com.studycourses.webclient.repository.*;
import com.studycourses.webclient.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AcademicDisciplineRepository academicDisciplineRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudyGroupRepository studyGroupRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<AcademicDiscipline> getListAcademicDiscipline() {
        return academicDisciplineRepository.findAll();
    }

    @Override
    public List<School> getListSchools() {
        return schoolRepository.findAll();
    }

    @Override
    public AcademicDiscipline getAcademicDisciplineById(Long id_discipline) {
        return academicDisciplineRepository.findById(id_discipline).get();
    }

    @Override
    public School getSchoolById(Long id_school) {
        return schoolRepository.findById(id_school).get();
    }

    @Override
    public void saveAcademicDiscipline(AcademicDiscipline academicDiscipline) {
        academicDisciplineRepository.save(academicDiscipline);
    }

    @Override
    public void saveSchool(School school) {
        schoolRepository.save(school);
    }

    @Override
    public void dropAcademicDiscipline(Long id_discipline) {
        if(!courseRepository.existsByAcademicDiscipline(academicDisciplineRepository.findById(id_discipline).get())){
            academicDisciplineRepository.deleteById(id_discipline);
        }
    }

    @Override
    public List<StudyGroup> getAllStudyGroups() {
        return studyGroupRepository.findAll(Sort.by("name").descending());
    }

    @Override
    public void dropGroupById(Long id_group) {
        if(!studentRepository.existsByStudyGroup(studyGroupRepository.findById(id_group).get())){
            studyGroupRepository.deleteById(id_group);
        }
    }
}
