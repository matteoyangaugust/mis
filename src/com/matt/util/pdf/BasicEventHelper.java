package com.matt.util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.uniong.util.ItextUtil;

import java.util.ArrayList;

public class BasicEventHelper extends PdfPageEventHelper {
    private Integer hasCountedPageNumber = 0;
    private boolean isFirstPageOfChapter = true;
    private Integer totalPage = 0;

    public ArrayList<PdfTemplate> tplList;
    private ArrayList<Integer> pageList;

    public ArrayList<Integer> getPageList(){
        return pageList;
    }

    public void onChapter(PdfWriter writer, Document document, float paragraphPosition, Paragraph title) {
        try{
            isFirstPageOfChapter = false;
            tplList = new ArrayList<PdfTemplate>();
            pageList = new ArrayList<Integer>();
            pageList.add(1);
            Rectangle pageSize = document.getPageSize();
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate template = cb.createTemplate(523, 50);
            tplList.add(template);
            Image img = Image.getInstance(template);
            img.setAbsolutePosition(
                    (pageSize.getWidth()/ 2)-25,
                    (pageSize.getHeight() / 10000));
            document.add(img);
        }catch(DocumentException e){
            e.printStackTrace();
        }
    }

    public void onChapterEnd(PdfWriter writer, Document document, float position) {
        Font normalMoreSmallfont = ItextUtil.getChineseFontInBlack("C:\\windows\\fonts\\MINGLIU.TTC,1", 8f, false);
        int currentPage = 1;
        for(PdfTemplate template : tplList){
            for(Integer pageNumber : pageList){
                if(pageNumber.equals(tplList.indexOf(template)+1)){
                    currentPage = 1;
                    if(pageList.indexOf(pageNumber) != pageList.size()-1){
                        totalPage = pageList.get(pageList.indexOf(pageNumber)+1) - pageNumber;
                    }else{
                        totalPage = writer.getPageNumber() - hasCountedPageNumber - pageNumber + 1;
                    }
                    hasCountedPageNumber += totalPage;
                    break;
                }
            }
            ColumnText ct = new ColumnText(template);
            ct.setSimpleColumn(
                    new Rectangle(0f, 0f, 60f, 40f));
            ct.addElement(new Paragraph("第" + currentPage++ + "頁/共" + totalPage + "頁", normalMoreSmallfont));
            try{
                ct.go();
            }catch(DocumentException e){
                e.printStackTrace();
            }
        }
    }

    public void onStartPage(PdfWriter writer, Document document) {
        if(!isFirstPageOfChapter){
            try{
                Rectangle pageSize = document.getPageSize();
                PdfContentByte cb = writer.getDirectContent();
                PdfTemplate template = cb.createTemplate(523, 50);
                tplList.add(template);
                Image img = Image.getInstance(template);
                img.setAbsolutePosition(
                        (pageSize.getWidth()/ 2)-25,
                        (pageSize.getHeight() / 10000));
                document.add(img);
            }catch(DocumentException e){
                e.printStackTrace();
            }
        }
    }
}
