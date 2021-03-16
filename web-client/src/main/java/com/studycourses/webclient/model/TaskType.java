package com.studycourses.webclient.model;

import javax.persistence.*;

@Entity
@Table(name="task_type")
public class TaskType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_type;

    private String name;

    public TaskType(){

    }
    public TaskType(String name){
        this.name=name;
    }

    public Long getId_type() {
        return id_type;
    }

    public void setId_type(Long id_type) {
        this.id_type = id_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
