package com.studycourses.webclient.model;

public class DataBean {
    private String FIO;
    private int grade;

    public  DataBean(String FIO,int grade){
        super();
        this.FIO=FIO;
        this.grade=grade;
    }

    public String getFio() {
        return FIO;
    }

    public void setFio(String FIO) {
        this.FIO = FIO;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
