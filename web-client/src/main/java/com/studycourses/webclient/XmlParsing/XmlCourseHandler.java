package com.studycourses.webclient.XmlParsing;

import com.studycourses.webclient.model.AcademicDiscipline;
import com.studycourses.webclient.model.Course;
import com.studycourses.webclient.model.Task;
import com.studycourses.webclient.model.TaskType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XmlCourseHandler extends DefaultHandler {
    private static final String COURSE="course";
    private static final String NAME="name";
    private static final String DISCIPLINE="discipline";
    private static final String DESCRIPTION="description";
    private static final String TASKS="tasks";
    private static final String TASK="task";
    private static final String TASKNAME="taskname";
    private static final String TYPE="type";
    private static final String TASKDESCRIPTION="taskdescription";
    private static final String DEADLINE="deadline";

    private Course course;
    private AcademicDiscipline academicDiscipline;
    private List<Task> tasks;
    private int typeIDText=1;
    private int typeIDTest=2;
    private String elementValue;

    public void startElement(String uri, String localName,String qName,
                             Attributes attributes) throws SAXException {

        switch (qName) {
            case COURSE:
                course= new Course();
                break;
            case TASKS:
                tasks=new ArrayList<>();
                break;
            case TASK:
                tasks.add(new Task());
        }
    }

    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        switch (qName) {
            case NAME:
                course.setName(elementValue);
                break;
            case DESCRIPTION:
                course.setDescription(elementValue);
                break;
            case DISCIPLINE:
                academicDiscipline=new AcademicDiscipline();
                academicDiscipline.setName(elementValue);
                break;
            case TASKNAME:
                latestTask().setName(elementValue);
                break;
            case TYPE:
                TaskType type=new TaskType(elementValue);
                if(elementValue.equals("TEXT")){
                    type.setId_type(Long.valueOf(typeIDText));
                }
                else{
                    type.setId_type(Long.valueOf(typeIDTest));
                }
                latestTask().setType(type);
                break;
            case TASKDESCRIPTION:
                latestTask().setDescription(elementValue);
                break;
            case DEADLINE:
                latestTask().setDeadline(elementValue);
                break;
        }
    }
    private Task latestTask() {
        int latestTaskIndex = tasks.size() - 1;
        return tasks.get(latestTaskIndex);
    }
    public void characters(char ch[], int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }
    public List<Task> getTasks() {
        return tasks;
    }
    public AcademicDiscipline getAcademicDiscipline(){
        return  academicDiscipline;
    }
    public Course getCourse(){
        return course;
    }

}
