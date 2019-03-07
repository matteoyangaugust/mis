package com.matt.bean;

import com.itextpdf.text.Font;
import com.matt.pdfView.AbstractITextPdfView;
import com.uniong.util.ItextUtil;

import java.io.File;

public class PdfStyleBean {
    /**
     * 微軟正黑體
     */
    protected final String kaiu =
            (new File(AbstractITextPdfView.class.getClassLoader().getResource("//").getPath())).getParent()+"\\resource\\fonts\\kaiu.ttf";//"C:\\windows\\fonts\\msjhbd.ttf";
    private Font normalMoreSmallFont;
    private Font normalSmallFont;
    private Font normalFont;
    private Font bigFont;
    private Font boldNormalMoreSmallFont;
    private Font boldNormalSmallFont;
    private Font boldNormalFont;
    private Font boldBigFont;
    public PdfStyleBean(){
        boldNormalMoreSmallFont = ItextUtil.getChineseFontInBlack(kaiu, 8f, true);
        boldNormalSmallFont = ItextUtil.getChineseFontInBlack(kaiu, 10f, true);
        boldNormalFont = ItextUtil.getChineseFontInBlack(kaiu, 11.0f, true);
        boldBigFont = ItextUtil.getChineseFontInBlack(kaiu, 14.0f, true);
        normalMoreSmallFont = ItextUtil.getChineseFontInBlack(kaiu, 8f, false);
        normalSmallFont = ItextUtil.getChineseFontInBlack(kaiu, 10f, false);
        normalFont = ItextUtil.getChineseFontInBlack(kaiu, 11.0f, false);
        bigFont = ItextUtil.getChineseFontInBlack(kaiu, 14.0f, false);
    }

    public Font getBoldNormalMoreSmallFont() {
        return boldNormalMoreSmallFont;
    }

    public void setBoldNormalMoreSmallFont(Font boldNormalMoreSmallFont) {
        this.boldNormalMoreSmallFont = boldNormalMoreSmallFont;
    }

    public Font getBoldNormalSmallFont() {
        return boldNormalSmallFont;
    }

    public void setBoldNormalSmallFont(Font boldNormalSmallFont) {
        this.boldNormalSmallFont = boldNormalSmallFont;
    }

    public Font getBoldNormalFont() {
        return boldNormalFont;
    }

    public void setBoldNormalFont(Font boldNormalFont) {
        this.boldNormalFont = boldNormalFont;
    }

    public Font getBoldBigFont() {
        return boldBigFont;
    }

    public void setBoldBigFont(Font boldBigFont) {
        this.boldBigFont = boldBigFont;
    }

    public Font getNormalMoreSmallFont() {
        return normalMoreSmallFont;
    }

    public void setNormalMoreSmallFont(Font normalMoreSmallFont) {
        this.normalMoreSmallFont = normalMoreSmallFont;
    }

    public Font getNormalSmallFont() {
        return normalSmallFont;
    }

    public void setNormalSmallFont(Font normalSmallFont) {
        this.normalSmallFont = normalSmallFont;
    }

    public Font getNormalFont() {
        return normalFont;
    }

    public void setNormalFont(Font normalFont) {
        this.normalFont = normalFont;
    }

    public Font getBigFont() {
        return bigFont;
    }

    public void setBigFont(Font bigFont) {
        this.bigFont = bigFont;
    }


}
