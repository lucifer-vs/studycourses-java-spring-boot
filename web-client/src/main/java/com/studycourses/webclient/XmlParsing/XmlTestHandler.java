package com.studycourses.webclient.XmlParsing;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XmlTestHandler extends DefaultHandler {
    private static final String QUESTION = "question";
    private static final String TEST = "test";
    private static final String NOMER = "nomer";
    private static final String TEXT = "text";
    private static final String A = "a";
    private static final String B = "b";
    private static final String C = "c";
    private static final String D = "d";

    private List<TestQuestion> test;
    private String elementValue;


    public void startElement(String uri, String localName,String qName,
                             Attributes attributes) throws SAXException {

        switch (qName) {
            case TEST:
                test= new ArrayList<>();
                break;
            case QUESTION:
                test.add(new TestQuestion());
        }



    }


    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        switch (qName) {
            case NOMER:
                latestQuestion().setNomer(elementValue);
                break;
            case TEXT:
                latestQuestion().setText(elementValue);
                break;
            case A:
                latestQuestion().setA(elementValue);
                break;
            case B:
                latestQuestion().setB(elementValue);
                break;
            case C:
                latestQuestion().setC(elementValue);
                break;
            case D:
                latestQuestion().setD(elementValue);
                break;
        }


    }
    private TestQuestion latestQuestion() {
        int latestQuestionIndex = test.size() - 1;
        return test.get(latestQuestionIndex);
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }
    public List<TestQuestion> getTest() {
        return test;
    }
}
