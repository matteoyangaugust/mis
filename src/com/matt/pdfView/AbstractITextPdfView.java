package com.matt.pdfView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.matt.util.MattUtil;
import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
public abstract class AbstractITextPdfView extends AbstractView {

	/**
	 * 微軟正黑體
	 */
    protected final String msJhengHeyPath = (new File(AbstractITextPdfView.class.getClassLoader().getResource("//").getPath())).getParent()+"\\msjhbd.ttf";//"C:\\windows\\fonts\\msjhbd.ttf";
    
    /**
     * 新細明體
     */
    protected final String mingPath = "C:\\windows\\fonts\\MINGLIU.TTC,1";
    
	public AbstractITextPdfView() {
        setContentType("application/pdf");
    }
	
	protected float transCm2Pound(float cm){
		return MattUtil.transCm2Pound(cm);
	}
 
    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }
         
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // IE workaround: write into byte array first.
        ByteArrayOutputStream baos = createTemporaryOutputStream();
        response.setHeader("Content-Disposition","attachment; filename=\"" + URLEncoder.encode(model.get("fileName").toString(), "UTF-8") +".pdf\"");
        // Apply preferences and build metadata.
        Document document = new Document();
        PdfWriter writer = newWriter(document, baos);
        prepareWriter(model, writer, request);
        buildPdfMetadata(model, document, request);
        // Build PDF document.
        buildPdfDocument(model, document, writer, request, response, baos);
        if(document.isOpen()){
        	document.close();
        }
 
        // Flush to HTTP response.
        writeToResponse(response, baos);
    }
 
    protected Document newDocument() {
        return new Document(PageSize.A4);
    }
     
    protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
        return PdfWriter.getInstance(document, os);
    }
     
    protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
            throws DocumentException {
        writer.setViewerPreferences(getViewerPreferences());
    }
     
    protected int getViewerPreferences() {
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage | PdfWriter.PageModeUseOutlines;
    }
     
    protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
    }
     
    protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response, ByteArrayOutputStream baos) throws Exception; 

}
