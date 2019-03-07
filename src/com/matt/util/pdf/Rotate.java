package com.matt.util.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;

public class Rotate extends PdfPageEventHelper {
    protected PdfNumber rotation = PdfPage.PORTRAIT;
    public void setRotation(PdfNumber rotation) {
        this.rotation = rotation;
    }
    public void onEndPage(PdfWriter writer, Document document) {
        writer.addPageDictEntry(PdfName.ROTATE, rotation);
    }
}
