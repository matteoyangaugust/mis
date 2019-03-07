package com.matt.bean;

public class PrintBean {
    public static final String TYPE_PDF = "pdf";
    public static final String TYPE_EXCEL = "excel";
    public static final String GENERATION_REPORT = "generation";
    public static final String STOCK_REPORT = "stock";
    public static final String GENERATION_PDF_VIEW = "GenerationView";
    public static final String STOCK_PDF_VIEW = "StockView";
    private String reportType;
    private String reportChoice;
    private String beginDate;
    private String endDate;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportChoice() {
        return reportChoice;
    }

    public void setReportChoice(String reportChoice) {
        this.reportChoice = reportChoice;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "\nPrintBean{" +
                "reportType='" + reportType + '\'' +
                ", reportChoice='" + reportChoice + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
