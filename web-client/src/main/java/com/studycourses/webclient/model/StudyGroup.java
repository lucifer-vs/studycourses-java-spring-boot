package com.studycourses.webclient.model;

import javax.persistence.*;

@Entity
@Table(name="study_group")
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_group;

    @ManyToOne
    @JoinColumn(name="id_school",nullable = false)
    private School school;

    private String name;


    public StudyGroup(){

    }
    public StudyGroup(String name){
        this.name=name;
    }

    public StudyGroup(String name,School school){
        this.name=name;
        this.school=school;
    }

    public Long getId_group() {
        return id_group;
    }

    public void setId_group(Long id_group) {
        this.id_group = id_group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
