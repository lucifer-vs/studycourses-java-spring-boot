package com.studycourses.webclient.service;

import com.studycourses.webclient.model.DataBean;
import com.studycourses.webclient.model.Progress;

import java.util.ArrayList;
import java.util.List;

public class ReportData {
   private List<Progress> progresses;
   private List<DataBean> dataBeans;

    public ReportData(List<Progress> progresses) {
        this.progresses=progresses;
    }

    public List<DataBean> getReportData(){
        dataBeans=new ArrayList<>();

        for (Progress i:
            progresses ) {
            dataBeans.add(new DataBean(
                    i.getStudent().getSurname()+" "+i.getStudent().getFirstname()+" "+i.getStudent().getMiddlename(),
                    i.getCourseGrade()
            ));
        }

        return  dataBeans;
    }
}
