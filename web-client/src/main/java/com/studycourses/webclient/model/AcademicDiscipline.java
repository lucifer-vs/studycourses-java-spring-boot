package com.studycourses.webclient.model;

import javax.persistence.*;

@Entity
@Table(name="academic_discipline")

public class AcademicDiscipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_discipline;

    private String name;
    private String scope;
    private String description;

    public AcademicDiscipline(){

    }
    public AcademicDiscipline(String name,String scope,String description){
        this.name=name;
        this.scope=scope;
        this.description=description;
    }

    public Long getId_discipline() {
        return id_discipline;
    }

    public void setId_discipline(Long id_discipline) {
        this.id_discipline = id_discipline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
