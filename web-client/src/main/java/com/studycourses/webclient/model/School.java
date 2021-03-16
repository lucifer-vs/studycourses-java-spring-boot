package com.studycourses.webclient.model;

import javax.persistence.*;

@Entity
@Table(name="school")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_school;

    private String name;

    public School(){

    }
    public School(String name){
        this.name=name;
    }

    public Long getId_school() {
        return id_school;
    }

    public void setId_school(Long id_school) {
        this.id_school = id_school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
