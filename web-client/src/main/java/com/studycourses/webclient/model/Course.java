package com.studycourses.webclient.model;

import javax.persistence.*;

@Entity
@Table(name="course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_course;

    @ManyToOne
    @JoinColumn(name = "id_teacher",nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name="id_discipline",nullable = false)
    private AcademicDiscipline academicDiscipline;

    private String name;
    private String description;
    private String image;

    public Course(){

    }

    public Course(String name,String description,String image){
        this.name=name;
        this.description=description;
        this.image=image;
    }

    public Long getId_course() {
        return id_course;
    }

    public void setId_course(Long id_course) {
        this.id_course = id_course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public AcademicDiscipline getAcademicDiscipline() {
        return academicDiscipline;
    }

    public void setAcademicDiscipline(AcademicDiscipline academicDiscipline) {
        this.academicDiscipline = academicDiscipline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
