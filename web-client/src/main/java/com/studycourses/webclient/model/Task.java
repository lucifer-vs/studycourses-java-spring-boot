package com.studycourses.webclient.model;

import javax.persistence.*;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_task;

    @ManyToOne
    @JoinColumn(name = "id_course",nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name="id_type")
    private TaskType type;

    @ManyToOne
    @JoinColumn(name="id_discipline")
    private AcademicDiscipline academicDiscipline;

    private String name;
    private String description;
    private String subdocument;
    private String deadline;

    public Task(){

    }
    public Task(String name, String description,String subdocument,String deadline){
        this.name=name;
        this.description=description;
        this.subdocument=subdocument;
        this.deadline=deadline;
    }

    public Long getId_task() {
        return id_task;
    }

    public void setId_task(Long id_task) {
        this.id_task = id_task;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
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

    public String getSubdocument() {
        return subdocument;
    }

    public void setSubdocument(String subdocument) {
        this.subdocument = subdocument;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
