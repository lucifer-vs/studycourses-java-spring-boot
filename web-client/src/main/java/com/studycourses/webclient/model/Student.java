package com.studycourses.webclient.model;

import javax.persistence.*;

@Entity
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_student;

    @ManyToOne
    @JoinColumn(name="id_group",nullable = false)
    private StudyGroup studyGroup;

    @ManyToOne
    @JoinColumn(name="id_role",nullable = false)
    private Role role;

    private String surname;
    private String firstname;
    private String middlename;
    private String foto;
    private String birth_date;
    private String email;
    private String login;
    private String password;

    public Student(){

    }
    public Student(String surname,String firstname,String middlename,
                   String foto,String birth_date,String email,
                   String login,String password){
        this.surname=surname;
        this.firstname=firstname;
        this.middlename=middlename;
        this.foto=foto;
        this.birth_date=birth_date;
        this.email=email;
        this.login=login;
        this.password=password;
    }

    public Long getId_student() {
        return id_student;
    }

    public void setId_student(Long id_student) {
        this.id_student = id_student;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup group) {
        this.studyGroup= group;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String date) {
        this.birth_date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
