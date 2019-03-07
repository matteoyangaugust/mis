package com.matt.pdfView;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.matt.bean.*;
import com.matt.model.Product_flowing_history;
import com.matt.util.DoubleUtil;
import com.matt.util.ListUtil;
import com.matt.util.MattUtil;
import com.matt.util.MisUtil;
import com.matt.util.pdf.BasicEventHelper;
import com.matt.util.pdf.ITextMattUtil;
import com.uniong.util.ItextUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public class GenerationView extends AbstractITextPdfView{
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response, ByteArrayOutputStream baos) throws Exception {
        buildGenerationPdf(document, writer, (ReportBean)model.get("reportBean"));
    }
    public void buildGenerationPdf(Document document, PdfWriter writer, ReportBean reportBean) throws DocumentException {
        BasicEventHelper helper = new BasicEventHelper();
        writer.setPageEvent(helper);
        ITextMattUtil.setPageHorizontal(document);
        document.open();
        setMaterialGenerationReport(document, writer, reportBean);
        setComponentGenerationReport(document, writer, reportBean);
        setProductGenerationReport(document, writer, reportBean);
    }

    private void setProductGenerationReport(Document document, PdfWriter writer, ReportBean reportBean) throws DocumentException {
        List<ProductFlowingHistoryBean> histories = MisUtil.getCombinedProductFlowingHistories(reportBean.getComponents(), reportBean.getComponentOfProducts()
                , reportBean.getProducts(), reportBean.getProduct_flowing_histories());
        ListUtil.sort(histories, "product_sn", "sn");
        PdfStyleBean styleBean = new PdfStyleBean();
        PdfPTable table_product = new PdfPTable(7);
        table_product.setWidthPercentage(106f);
        table_product.setSplitRows(false);
        ItextUtil.setMergeCellAlignLeftNoBorder(1, 7, table_product, styleBean.getNormalMoreSmallFont(), 1,
                "Print Time:" + MattUtil.getDateTimeWithUnitWithoutBreak(MattUtil.getDateTime()), 5f);
        ItextUtil.setMergeCellAlignCenter(1, 7, table_product, styleBean.getBoldBigFont(), 5, "Build Product Report", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getBoldNormalFont(), 5, "Build/Sell Time", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getBoldNormalFont(), 5, "Action", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getBoldNormalFont(), 5, "Product Code", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getBoldNormalFont(), 5, "Product Name", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getBoldNormalFont(), 5, "Used Component", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getBoldNormalFont(), 5, "Stock", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getBoldNormalFont(), 5, "Quantity", 20f);
        int total = 0;
        int subtotal = 0;
        int subtotalCounter = 0;
        int prevProductSn = 0;
        for(ProductFlowingHistoryBean history : histories){
            if(!history.getProduct_sn().equals(prevProductSn) && histories.indexOf(history) != 0){
                ItextUtil.setMergeCellAlignRight(1, 6, table_product, styleBean.getNormalFont(), 5, "Subtotal", 20f);
                ItextUtil.setMergeCellAlignRight(1, 1, table_product, styleBean.getNormalFont(), 5,
                        MattUtil.transNumberToMoneyNumber((double)subtotal) + "", 20f);
                subtotalCounter++;
                //小計歸0
                subtotal = 0;
            }
            prevProductSn = history.getProduct_sn();
            ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getNormalFont(), 5,
                    history.getUpdateTimeWithUnit(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getNormalFont(), 5,
                    history.getActionString(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getNormalFont(), 5,
                    history.getProduct().getIdentifier(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getNormalFont(), 5,
                    history.getProduct().getName(), 20f);
            if(history.getAction().equals(Product_flowing_history.ACTION_SELL)){
                ItextUtil.setMergeCellAlignRight(1, 1, table_product, styleBean.getNormalFont(), 5, "", 20f);
            }else{
                ItextUtil.setMergeCellAlignRight(1, 1, table_product, styleBean.getNormalFont(), 5,
                        MisUtil.getComponentListReportString(reportBean.getComponentOfProducts(), reportBean.getComponents(), history), 20f);
            }
            ItextUtil.setMergeCellAlignRight(1, 1, table_product, styleBean.getNormalFont(), 5,
                    history.getRemain() + "", 20f);
            ItextUtil.setMergeCellAlignRight(1, 1, table_product, styleBean.getNormalFont(), 5,
                    history.getAmount() + "", 20f);
            subtotal += history.getAmount();
            total += history.getAmount();
            if(histories.indexOf(history) == histories.size() - 1) {
                subtotalCounter++;
                ItextUtil.setMergeCellAlignRight(1, 6, table_product, styleBean.getNormalFont(), 5, "Subtotal", 20f);
                ItextUtil.setMergeCellAlignRight(1, 1, table_product, styleBean.getNormalFont(), 5,
                        MattUtil.transNumberToMoneyNumber((double)subtotal) + "", 20f);
            }
        }
        ItextUtil.setMergeCellAlignRight(1, 6, table_product, styleBean.getNormalFont(), 5, "Total", 20f);
        ItextUtil.setMergeCellAlignRight(1, 1, table_product, styleBean.getNormalFont(), 5,
                total + "", 20f);
        document.add(ItextUtil.addBookmark("Build Product Report", table_product));
    }

    private void setComponentGenerationReport(Document document, PdfWriter writer, ReportBean reportBean) throws DocumentException {
        List<ComponentFlowingHistoryBean> histories = MisUtil.getCombinedComponentFlowingHistories(reportBean.getComponent_flowing_histories(),
                reportBean.getComponents(), reportBean.getMaterials(), reportBean.getCategories(), reportBean.getElements_of_components());
        ListUtil.sort(histories, "component_sn", "sn");
        PdfStyleBean styleBean = new PdfStyleBean();
        PdfPTable table_component = new PdfPTable(11);
        table_component.setWidthPercentage(106f);
        table_component.setSplitRows(false);
        ItextUtil.setMergeCellAlignLeftNoBorder(1, 11, table_component, styleBean.getNormalMoreSmallFont(), 1,
                "Print Time:" + MattUtil.getDateTimeWithUnitWithoutBreak(MattUtil.getDateTime()), 5f);
        ItextUtil.setMergeCellAlignCenter(1, 11, table_component, styleBean.getBoldBigFont(), 5, "Build Component Report", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Build Time", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Component Code", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Component Name", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Procedure", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 2, table_component, styleBean.getBoldNormalFont(), 5, "Used Material/Component", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Amount of Procedure", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Quantity per Procedure", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Total Quantity", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Stock", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Price", 20f);
        //總計的價錢
        Double totalFee = 0.0;
        //前一個history的component_sn
        int prevComponentSn = 0;
        //小計的價錢
        Double subTotalFee = 0.0;
        //有幾個小計列
        int subTotalCounter = 0;
        for(ComponentFlowingHistoryBean history : histories) {
            if (!history.getComponent_sn().equals(prevComponentSn) && histories.indexOf(history) != 0) {
                ItextUtil.setMergeCellAlignRight(1, 10, table_component, styleBean.getNormalFont(), 5, "Subtotal", 20f);
                ItextUtil.setMergeCellAlignRight(1, 1, table_component, styleBean.getNormalFont(), 5,
                        MattUtil.transNumberToMoneyNumber(subTotalFee) + "NTD", 20f);
                subTotalCounter++;
                //小計歸0
                subTotalFee = 0.0;
            }
            prevComponentSn = history.getComponent_sn();
            //一般資料
            ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getNormalFont(), 5, history.getUpdateTimeWithUnit(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getNormalFont(), 5, history.getComponent().getIdentifier(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getNormalFont(), 5, history.getComponent().getName(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getNormalFont(), 5, history.getProcessCategory().getName(), 20f);
            ItextUtil.setMergeCellAlignRight(1, 2, table_component, styleBean.getNormalFont(), 5,
                    history.getProcessCategory().getRelation().contains("ROLE_PURCHASE") ? "" : MisUtil.getMaterialOfComponentStringForReport(history), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getNormalFont(), 5,
                    history.getProcessCategory().getRelation().contains("ROLE_PURCHASE") ? "" : String.valueOf(history.getProcess_amount()), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getNormalFont(), 5,
                    history.getProcessCategory().getRelation().contains("ROLE_PURCHASE") ? "" : String.valueOf(history.getComponent().getProcess_yield()), 20f);
            ItextUtil.setMergeCellAlignRight(1, 1, table_component, styleBean.getNormalFont(), 5, String.valueOf(history.getAmount()) + "", 20f);
            ItextUtil.setMergeCellAlignRight(1, 1, table_component, styleBean.getNormalFont(), 5,
                    String.valueOf(history.getRemain()), 20f);
            ItextUtil.setMergeCellAlignRight(1, 1, table_component, styleBean.getNormalFont(), 5,
                    MattUtil.transNumberToMoneyNumber(history.getPurchase_fee()) + "NTD", 20f);
            //總計累加
            totalFee = DoubleUtil.add(totalFee, history.getPurchase_fee());
            //小計累加
            subTotalFee = DoubleUtil.add(subTotalFee, history.getPurchase_fee());
            //最後一次小計
            if(histories.indexOf(history) == histories.size() - 1){
                subTotalCounter++;
                ItextUtil.setMergeCellAlignRight(1, 10, table_component, styleBean.getNormalFont(), 5, "Subtotal", 20f);
                ItextUtil.setMergeCellAlignRight(1, 1, table_component, styleBean.getNormalFont(), 5,
                        MattUtil.transNumberToMoneyNumber(subTotalFee) + "NTD", 20f);
            }
        }
        ItextUtil.setMergeCellAlignRightNoBorder(1, 10, table_component, styleBean.getNormalFont(), 5, "Total", 20f);
        ItextUtil.setMergeCellAlignRightNoBorder(1, 1, table_component, styleBean.getNormalFont(), 5,
                MattUtil.transNumberToMoneyNumber(totalFee) + "NTD", 20f);
        document.add(ItextUtil.addBookmark("Build Component Report", table_component));
    }

    private void setMaterialGenerationReport(Document document, PdfWriter writer, ReportBean reportBean) throws DocumentException {
        List<MaterialFlowingHistoryBean> histories = MisUtil.getCombinedMaterialFlowingHistories(reportBean.getMaterial_flowing_histories(),
                reportBean.getMaterials(), reportBean.getSuppliers());
        ListUtil.sort(histories, "material_sn", "buying_time");
        PdfStyleBean styleBean = new PdfStyleBean();
        PdfPTable table_material = new PdfPTable(8);
        table_material.setWidthPercentage(106f);
        table_material.setSplitRows(false);
        ItextUtil.setMergeCellAlignLeftNoBorder(1, 8, table_material, styleBean.getNormalMoreSmallFont(), 1,
                "Print Time:" + MattUtil.getDateTimeWithUnitWithoutBreak(MattUtil.getDateTime()), 5f);
        ItextUtil.setMergeCellAlignCenter(1, 8, table_material, styleBean.getBoldBigFont(), 5, "Buy Material Report", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getBoldNormalFont(), 5, "Update Time", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getBoldNormalFont(), 5, "Buying Time", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getBoldNormalFont(), 5, "Material Code", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getBoldNormalFont(), 5, "Material Name", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getBoldNormalFont(), 5, "Supplier", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getBoldNormalFont(), 5, "Quantity", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getBoldNormalFont(), 5, "Stock", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getBoldNormalFont(), 5, "Price", 20f);

        //總計的價錢
        Double totalFee = 0.0;
        //前一個history的Material_sn
        int prevMaterialSn = 0;
        //小計的價錢
        Double subTotalFee = 0.0;
        //有幾個小計列
        int subTotalCounter = 0;
        for(MaterialFlowingHistoryBean history : histories){
            if(!history.getMaterial_sn().equals(prevMaterialSn) && histories.indexOf(history) != 0){
                ItextUtil.setMergeCellAlignRight(1, 7, table_material, styleBean.getNormalFont(), 5, "Subtotal", 20f);
                ItextUtil.setMergeCellAlignRight(1, 1, table_material, styleBean.getNormalFont(), 5,
                        MattUtil.transNumberToMoneyNumber(subTotalFee) + "NTD", 20f);
                subTotalCounter++;
                //小計歸0
                subTotalFee = 0.0;
            }
            prevMaterialSn = history.getMaterial_sn();
            ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getNormalFont(), 5, history.getUpdateTimeWithUnit(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getNormalFont(), 5, history.getBuyingTimeWithUnit(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getNormalFont(), 5, history.getMaterialIdentifier(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getNormalFont(), 5, history.getMaterialName(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getNormalFont(), 5, history.getSupplierName(), 20f);
            ItextUtil.setMergeCellAlignRight(1, 1, table_material, styleBean.getNormalFont(), 5,
                    String.valueOf(history.getQuantity() == history.getQuantity().intValue() ? history.getQuantity().intValue() : history.getQuantity()) + history.getUnitName(), 20f);
            ItextUtil.setMergeCellAlignRight(1, 1, table_material, styleBean.getNormalFont(), 5,
                    String.valueOf(history.getRemain() == history.getRemain().intValue() ? history.getRemain().intValue() : history.getRemain()) + history.getUnitName(), 20f);
            ItextUtil.setMergeCellAlignRight(1, 1, table_material, styleBean.getNormalFont(), 5,
                    MattUtil.transNumberToMoneyNumber(history.getFee()) + "NTD", 20f);
            //總計累加
            totalFee = DoubleUtil.add(totalFee, history.getFee());
            //小計累加
            subTotalFee = DoubleUtil.add(subTotalFee, history.getFee());
            //最後一次小計
            if(histories.indexOf(history) == histories.size() - 1){
                ItextUtil.setMergeCellAlignRight(1, 7, table_material, styleBean.getNormalFont(), 5, "Subtotal", 20f);
                ItextUtil.setMergeCellAlignRight(1, 1, table_material, styleBean.getNormalFont(), 5,
                        MattUtil.transNumberToMoneyNumber(subTotalFee) + "NTD", 20f);
            }
        }
        ItextUtil.setMergeCellAlignRightNoBorder(1, 7, table_material, styleBean.getNormalFont(), 5, "Total", 20f);
        ItextUtil.setMergeCellAlignRightNoBorder(1, 1, table_material, styleBean.getNormalFont(), 5, MattUtil.transNumberToMoneyNumber(totalFee) + "NTD", 20f);
        document.add(ItextUtil.addBookmark("But Material Report", table_material));
    }
}


