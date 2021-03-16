package com.studycourses.webclient.XmlParsing;

import com.studycourses.webclient.model.Role;
import com.studycourses.webclient.model.Teacher;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XmlTeacherHandler extends DefaultHandler {

    private static final String TEACHERS="teachers";
    private static final String TEACHER="teacher";
    private static final String SURNAME="surname";
    private static final String FIRSTNAME="firstname";
    private static final String MIDDLENAME="middlename";
    private static final String QUALIFICATION="qualification";
    private static final String EMAIL="email";
    private static final String LOGIN="login";
    private static final String PASSWORD="password";
    private static final String ROLE="role";

    private List<Teacher> teachers;
    private int teacherIndex=1;
    private int adminIndex=3;
    private String elementValue;

    @Override
    public void startElement(String uri, String localName,String qName,
                             Attributes attributes) throws SAXException {

        switch (qName) {
            case TEACHERS:
                teachers=new ArrayList<>();
                break;
            case TEACHER:
                teachers.add(new Teacher());
                break;

        }
    }
    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        switch (qName) {

            case SURNAME:
                latestTeacher().setSurname(elementValue);
                break;
            case FIRSTNAME:
                latestTeacher().setFirstname(elementValue);
                break;
            case MIDDLENAME:
                latestTeacher().setMiddlename(elementValue);
                break;
            case QUALIFICATION:
                latestTeacher().setQualification(elementValue);
                break;
            case EMAIL:
                latestTeacher().setEmail(elementValue);
                break;
            case LOGIN:
                latestTeacher().setLogin(elementValue);
                break;
            case PASSWORD:
                latestTeacher().setPassword(elementValue);
                break;
            case ROLE:
                Role role=new Role();
                if(elementValue.equals("TEACHER")){
                    role.setId_role(Long.valueOf(teacherIndex));
                }
                else{
                    role.setId_role(Long.valueOf(adminIndex));
                }
                latestTeacher().setRole(role);
                break;
        }
    }
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    private Teacher latestTeacher() {
        int latestTeacherIndex = teachers.size() - 1;
        return teachers.get(latestTeacherIndex);
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }
}
