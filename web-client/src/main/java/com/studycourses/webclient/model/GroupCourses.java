package com.studycourses.webclient.model;


import javax.persistence.*;

@Entity
@Table(name = "group_courses")
public class GroupCourses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_relation;

    @ManyToOne
    @JoinColumn(name = "id_course",nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "id_group",nullable = false)
    private StudyGroup studyGroup;

    public GroupCourses(){

    }

    public Long getId_relation() {
        return id_relation;
    }

    public void setId_relation(Long id_relation) {
        this.id_relation = id_relation;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }
}
