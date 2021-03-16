package com.studycourses.webclient.model;

import javax.persistence.*;

@Entity
@Table(name = "task_performance")
public class TaskPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_perfomance;

    @ManyToOne
    @JoinColumn(name="id_student",nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "id_task")
    private Task task;

    private String text_response;
    private String subdocument;
    private String completion;
    private Integer grade;

    public TaskPerformance(){

    }
    public TaskPerformance(String text_response,String subdocument,String completion, Integer grade){
        this.text_response=text_response;
        this.subdocument=subdocument;
        this.completion=completion;
        this.grade=grade;
    }
    public TaskPerformance(Student student,Task task){
        this.student=student;
        this.task=task;
    }

    public Long getId_performance() {
        return id_perfomance;
    }

    public void setId_performance(Long id_performance) {
        this.id_perfomance = id_performance;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getText_response() {
        return text_response;
    }

    public void setText_responce(String text_response) {
        this.text_response = text_response;
    }

    public String getSubdocument() {
        return subdocument;
    }

    public void setSubdocument(String subdocument) {
        this.subdocument = subdocument;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
