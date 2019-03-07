package com.matt.controller.manage;

import com.matt.bean.PrintBean;
import com.matt.bean.ResultBean;
import com.matt.controller.BaseController;
import com.matt.service.manage.ManagePrintService;
import com.matt.util.POIUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value="/manage/print/")
public class ManagePrintController extends BaseController{

    @Autowired
    private ManagePrintService managePrintService;

    private final String PRINT_VIEW = "manage/print/toPrint";
    @RequestMapping(value = "toPrint.do")
    public String toPrint(Model model){
        model.addAttribute("result", new ResultBean(true, ""));
        return super.toView(PRINT_VIEW, model);
    }

    @RequestMapping(value="printExcel.do")
    public void printExcel(PrintBean printBean, HttpServletResponse response) throws IOException {
        try {
            HSSFWorkbook workbook = managePrintService.getExcel(printBean, super.getUser());
            response.setContentType("application/vnd.ms-excel");
            if(printBean.getReportChoice().equals(PrintBean.GENERATION_REPORT)){
                POIUtil.outputExcel(response, "ManufactureReport.xls", workbook);
            }else{
                POIUtil.outputExcel(response, "StockReport.xls", workbook);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @RequestMapping(value="printPdf.do")
    public ModelAndView printPdf(PrintBean printBean, Model model) throws IOException {
        try{
            String pdfView = managePrintService.getPdfView(printBean);
            if(pdfView.equals(PrintBean.GENERATION_PDF_VIEW)){
                model.addAttribute("fileName", "ManufactureReport");
            }else{
                model.addAttribute("fileName", "StockReport");
            }
            model.addAttribute("reportBean", managePrintService.getReportData(printBean, super.getUser()));
            return new ModelAndView(pdfView, "model", model);
        }catch (Exception e){
            log.error("列印錯誤");
            return null;
        }
    }
}
