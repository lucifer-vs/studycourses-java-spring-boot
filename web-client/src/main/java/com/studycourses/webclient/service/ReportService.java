package com.studycourses.webclient.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.studycourses.webclient.model.Course;
import com.studycourses.webclient.model.DataBean;
import com.studycourses.webclient.model.StudyGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ReportService {

    private String uploadPath="D://GitHub//studycourses-springboot//filestorage";

    @Value("${report.name}")
    private String reportName;

    public void createReport(List<DataBean> data, StudyGroup studyGroup, Course course) throws Exception {
        String path;
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(uploadPath+reportName));

        document.open();
        BaseFont bf = BaseFont.createFont(uploadPath+"//arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED); //подключаем файл шрифта, который поддерживает кириллицу
        Font font = new Font(bf,14);

        Paragraph paragraph = new Paragraph("Ведомость успеваемости", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        Chunk currDate = new Chunk("Дата формирования: "+new SimpleDateFormat("dd-MM-yyyy").format(new Date()), font);
        Chunk courseName = new Chunk("\n Курс: "+course.getName(), font);
        Chunk school = new Chunk("\n Учебное заведение: "+studyGroup.getSchool().getName(), font);
        Chunk group = new Chunk("\n Класс: "+studyGroup.getName(), font);

        PdfPTable table = new PdfPTable(2);
        addTableHeader(table,font);

        addRows(table,font,data);

        document.add(paragraph);
        document.add(currDate);
        document.add(courseName);
        document.add(school);
        document.add(group);
        document.add(table);
        document.close();

    }
    private void addTableHeader(PdfPTable table, Font font) {
        Stream.of("ФИО", "Оценка")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(Color.lightGray);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle,font));
                    table.addCell(header);
                });
    }
    private void addRows(PdfPTable table,Font font,List<DataBean> data) {

        for (DataBean i:
             data) {
            table.addCell(new Paragraph(i.getFio(),font));
            table.addCell(new Paragraph(Integer.toString(i.getGrade()),font));
        }

    }

    public String getReportPath(){
        return uploadPath+reportName;
    }
}
