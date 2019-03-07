package com.matt.util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;

public class ITextMattUtil {

    public static void setPageHorizontal(Document document){
        Rectangle pageSize = new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth());
        pageSize.rotate();
        document.setPageSize(pageSize);
    }

    public static Section addSection(String sectionTitle, Chapter chapter, PdfPTable table, Font font){
        Section section = chapter.addSection(new Paragraph(sectionTitle, font));
        section.add(table);
        section.setIndentation(10);
        section.setIndentationLeft(10);
        section.setBookmarkOpen(false);
        return section;
    }
}
