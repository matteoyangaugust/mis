package com.matt.bean;

import com.matt.util.POIUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelStyleBean {
    private HSSFCellStyle datetimeCellStyle;
    private HSSFCellStyle titleCellStyle;
    private HSSFCellStyle itemNameCellStyle;
    private HSSFCellStyle contentCellStyle;
    private HSSFCellStyle contentRightCellStyle;
    private HSSFCellStyle contentRightCellStyleWithoutBorder;
    private HSSFFont datetimeFont;
    private HSSFFont titleFont;
    private HSSFFont itemNameFont;
    private HSSFFont contentFont;

    public ExcelStyleBean(HSSFWorkbook workbook){
        //標題Style
        this.datetimeCellStyle = POIUtil.getStyle(workbook, false, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER);
        this.datetimeFont = POIUtil.setFont(workbook, "標楷體", (short) 9, false);
        this.datetimeCellStyle.setFont(this.datetimeFont);
        //標題Style
        this.titleCellStyle = POIUtil.getStyle(workbook, true);
        this.titleFont = POIUtil.setFont(workbook, "標楷體", (short) 14, true);
        this.titleCellStyle.setFont(this.titleFont);
        //項目名字Style
        this.itemNameCellStyle = POIUtil.getStyle(workbook, true);
        this.itemNameFont = POIUtil.setFont(workbook, "標楷體", (short) 12, true);
        this.itemNameCellStyle.setFont(this.itemNameFont);
        //內容Style
        this.contentCellStyle = POIUtil.getStyle(workbook, true);
        this.contentFont = POIUtil.setFont(workbook, "標楷體", (short) 12, false);
        this.contentCellStyle.setFont(this.contentFont);
        //內容Style_right
        this.contentRightCellStyle = POIUtil.getStyle(workbook, true, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER);
        this.contentRightCellStyle.setFont(this.contentFont);
        //內容Style_right 無外框
        this.contentRightCellStyleWithoutBorder = POIUtil.getStyle(workbook, false, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER);
        this.contentRightCellStyleWithoutBorder.setFont(this.contentFont);
    }


    public HSSFCellStyle getContentRightCellStyleWithoutBorder() {
        return contentRightCellStyleWithoutBorder;
    }

    public void setContentRightCellStyleWithoutBorder(HSSFCellStyle contentRightCellStyleWithoutBorder) {
        this.contentRightCellStyleWithoutBorder = contentRightCellStyleWithoutBorder;
    }

    public HSSFCellStyle getDatetimeCellStyle() {
        return datetimeCellStyle;
    }

    public void setDatetimeCellStyle(HSSFCellStyle datetimeCellStyle) {
        this.datetimeCellStyle = datetimeCellStyle;
    }

    public HSSFCellStyle getTitleCellStyle() {
        return titleCellStyle;
    }

    public void setTitleCellStyle(HSSFCellStyle titleCellStyle) {
        this.titleCellStyle = titleCellStyle;
    }

    public HSSFCellStyle getItemNameCellStyle() {
        return itemNameCellStyle;
    }

    public void setItemNameCellStyle(HSSFCellStyle itemNameCellStyle) {
        this.itemNameCellStyle = itemNameCellStyle;
    }

    public HSSFCellStyle getContentCellStyle() {
        return contentCellStyle;
    }

    public void setContentCellStyle(HSSFCellStyle contentCellStyle) {
        this.contentCellStyle = contentCellStyle;
    }

    public HSSFCellStyle getContentRightCellStyle() {
        return contentRightCellStyle;
    }

    public void setContentRightCellStyle(HSSFCellStyle contentRightCellStyle) {
        this.contentRightCellStyle = contentRightCellStyle;
    }

    public HSSFFont getDatetimeFont() {
        return datetimeFont;
    }

    public void setDatetimeFont(HSSFFont datetimeFont) {
        this.datetimeFont = datetimeFont;
    }

    public HSSFFont getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(HSSFFont titleFont) {
        this.titleFont = titleFont;
    }

    public HSSFFont getItemNameFont() {
        return itemNameFont;
    }

    public void setItemNameFont(HSSFFont itemNameFont) {
        this.itemNameFont = itemNameFont;
    }

    public HSSFFont getContentFont() {
        return contentFont;
    }

    public void setContentFont(HSSFFont contentFont) {
        this.contentFont = contentFont;
    }
}
