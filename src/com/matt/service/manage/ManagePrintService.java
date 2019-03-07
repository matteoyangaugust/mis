package com.matt.service.manage;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.matt.bean.PrintBean;
import com.matt.bean.ReportBean;
import com.matt.model.System_user;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public interface ManagePrintService {
    public HSSFWorkbook getExcel(PrintBean printBean, System_user user);

    public String getPdfView(PrintBean printBean);

    public ReportBean getReportData(PrintBean printBean, System_user user) throws Exception;
}
