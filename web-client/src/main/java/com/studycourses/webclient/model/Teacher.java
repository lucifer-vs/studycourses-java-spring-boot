package com.studycourses.webclient.model;

import javax.persistence.*;

@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_teacher;

    @ManyToOne
    @JoinColumn(name = "id_role",nullable = false)
    private Role role;

    private String surname;
    private String firstname;
    private String middlename;
    private String foto;
    private String qualification;
    private String email;
    private String login;
    private String password;

    public Teacher(){

    }
    public Teacher(String surname,String firstname,String middlename,
                   String foto,String qualification,String email,
                   String login,String password){
        this.surname=surname;
        this.firstname=firstname;
        this.middlename=middlename;
        this.foto=foto;
        this.qualification=qualification;
        this.email=email;
        this.login=login;
        this.password=password;
    }

    public Long getId_teacher() {
        return id_teacher;
    }

    public void setId_teacher(Long id_teacher) {
        this.id_teacher = id_teacher;
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
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
