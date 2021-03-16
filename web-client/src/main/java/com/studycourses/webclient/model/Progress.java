package com.studycourses.webclient.model;

import javax.persistence.*;

@Entity
@Table(name = "progress")
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_progress;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "id_course")
    private Course course;

    private int course_grade;
    public Progress(){

    }
    public Progress(Student student,Course course){
        this.student=student;
        this.course=course;
    }
    public Progress(int course_grade){
        this.course_grade=course_grade;
    }

    public Long getId_progress() {
        return id_progress;
    }

    public void setId_progress(Long id_progress) {
        this.id_progress = id_progress;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getCourseGrade() {
        return course_grade;
    }

    public void setCourseGrade(int courseGrade) {
        this.course_grade = courseGrade;
    }
}
