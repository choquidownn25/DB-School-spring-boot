package com.proyecto.account.util;

import com.proyecto.account.bean.MyBean;
import com.proyecto.account.bean.implementation.MyBeanTwoImpl;
import com.proyecto.account.model.Empleado;
import com.proyecto.account.repository.IEmpleadoRepository;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
//el archivo creado en el paso 1
public class PDFGenerator implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(PDFGenerator.class);
    @Autowired
    private Environment env;
    @Value("${pdfDir}")
    private String pdfDir;

    @Value("${reportFileName}")
    private String reportFileName;

    @Value("${reportFileNameDateFormat}")
    private String reportFileNameDateFormat;

    @Value("${localDateFormat}")
    private String localDateFormat;

    @Value("${logoImgPath}")
    private String logoImgPath;

    @Value("${logoImgScale}")
    private Float[] logoImgScale;

    @Value("${currencySymbol}")
    private String currencySymbol;

    @Value("${table_noOfColumns}")
    private int noOfColumns;

    @Value("${table.columnNames}")
    private List<String> columnNames;

    @Autowired
    IEmpleadoRepository eRepo;

    private static Font COURIER = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
    private static Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 16, Font.BOLD);
    private static Font COURIER_SMALL_FOOTER = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
    private String datoImage;
    private String datoDate;
    private String datoNombre;
    //Genera reporte
    public ByteArrayInputStream generatePdfReport(List<Empleado> empleado, String imagen, Float[] logoImgScale, String reportFileName) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            addLogo(document, imagen, logoImgScale);
            addDocTitle(document);
            createTable(document,4, empleado);
            addFooter(document, reportFileName);
            document.close();
            System.out.println("------------------Your PDF Report is ready!-------------------------");

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            logger.error(e.toString());
            e.printStackTrace();

        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Bean
    public ILogo myLogoBean(){
        datoImage = String.valueOf( new LogoImpl(logoImgPath));
        return new LogoImpl(logoImgPath);
    }

    private void addLogo(Document document, String imagen, Float[] logoImgScale) {
        try {

            Image img = Image.getInstance(imagen);
            img.scalePercent(logoImgScale[0], logoImgScale[1]);
            img.setAlignment(Element.ALIGN_RIGHT);
            document.add(img);
        } catch (DocumentException | IOException e) {
            // TODO Auto-generated catch block
            logger.error(e.toString());
            e.printStackTrace();
        }
    }

    private void addDocTitle(Document document) throws DocumentException {
        String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Paragraph p1 = new Paragraph();
        leaveEmptyLine(p1, 1);
        p1.add(new Paragraph(reportFileName, COURIER));
        p1.setAlignment(Element.ALIGN_CENTER);
        leaveEmptyLine(p1, 1);
        p1.add(new Paragraph("Report generated on " + localDateString, COURIER_SMALL));

        document.add(p1);

    }

    private void createTable(Document document, int noOfColumns, List<Empleado> empleado) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        leaveEmptyLine(paragraph, 3);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(noOfColumns);

        for(int i=0; i<noOfColumns; i++) {
            PdfPCell cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.CYAN);
            table.addCell(cell);
        }

        table.setHeaderRows(1);
        getDbData(table, empleado);
        document.add(table);
    }



    private void addFooter(Document document, String reportFileName) throws DocumentException {

        Paragraph p2 = new Paragraph();
        leaveEmptyLine(p2, 3);
        p2.setAlignment(Element.ALIGN_MIDDLE);
        p2.add(new Paragraph(
                "------------------------End Of :" +reportFileName+"------------------------",
                COURIER_SMALL_FOOTER));

        document.add(p2);
    }

    private static void leaveEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private String getPdfNameWithDate() {
        String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss.SSSZ"));
        return datoNombre+"-"+localDateString+".pdf";
    }

    private List<Empleado> getDbData(PdfPTable table, List<Empleado> empleado) {
        //Listar en db
        List<Empleado>list=new ArrayList<>();
        //Recorrido de la lista
        for (Empleado empleados: empleado){
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            //Datos
            table.addCell(empleados.getId().toString());
            table.addCell(empleados.getNombre());
            table.addCell(empleados.getCiudad());
            table.addCell(empleados.getTelefono());
            list.add(empleados);
        }
        return  list;
    }

    @Override
    public void run(String... args) throws Exception {
        datoImage = logoImgPath;
        datoDate =  localDateFormat;
        datoNombre= pdfDir+reportFileName;
    }
}
