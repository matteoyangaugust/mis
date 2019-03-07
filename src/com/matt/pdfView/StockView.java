package com.matt.pdfView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.matt.bean.ComponentBean;
import com.matt.bean.PdfStyleBean;
import com.matt.bean.ProductBean;
import com.matt.bean.ReportBean;
import com.matt.model.Component_of_product;
import com.matt.model.Material;
import com.matt.model.Material_supplier;
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

public class StockView extends AbstractITextPdfView{
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response, ByteArrayOutputStream baos) throws Exception {
        buildStockPdf(document, writer, (ReportBean)model.get("reportBean"));
    }

    private void buildStockPdf(Document document, PdfWriter writer, ReportBean reportBean) throws DocumentException {
        BasicEventHelper helper = new BasicEventHelper();
        writer.setPageEvent(helper);
        ITextMattUtil.setPageHorizontal(document);
        document.open();
        setMaterialStockReport(document, reportBean);
        setComponentStockReport(document, reportBean);
        setProductStockReport(document, reportBean);
    }

    private void setMaterialStockReport(Document document, ReportBean reportBean) throws DocumentException {
        PdfStyleBean styleBean = new PdfStyleBean();
        PdfPTable table_material = new PdfPTable(5);
        table_material.setWidthPercentage(106f);
        table_material.setSplitRows(false);
        ItextUtil.setMergeCellAlignLeftNoBorder(1, 5, table_material, styleBean.getNormalMoreSmallFont(), 1,
                "Print Time:" + MattUtil.getDateTimeWithUnitWithoutBreak(MattUtil.getDateTime()), 5f);
        ItextUtil.setMergeCellAlignCenter(1, 5, table_material, styleBean.getBoldBigFont(), 5, "Material Stock Report", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getBoldNormalFont(), 5, "Material Code", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getBoldNormalFont(), 5, "Material Name", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getBoldNormalFont(), 5, "Supplier", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getBoldNormalFont(), 5, "Price", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getBoldNormalFont(), 5, "Stock", 20f);

        for(Material material : reportBean.getMaterials()){
            ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getNormalFont(), 5,
                    material.getIdentifier(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getNormalFont(), 5,
                    material.getName(), 20f);
            for(Material_supplier supplier : reportBean.getSuppliers()) {
                if (material.getSupplier().equals(supplier.getSn())) {
                    ItextUtil.setMergeCellAlignCenter(1, 1, table_material, styleBean.getNormalFont(), 5,
                            supplier.getName(), 20f);
                }
            }
            ItextUtil.setMergeCellAlignRight(1, 1, table_material, styleBean.getNormalFont(), 5,
                    MattUtil.transNumberToMoneyNumber(material.getPrice()) + "NTD", 20f);
            ItextUtil.setMergeCellAlignRight(1, 1, table_material, styleBean.getNormalFont(), 5,
                    (material.getQuantity() == material.getQuantity().intValue() ?
                            material.getQuantity().intValue() : material.getQuantity()) +
                            MisUtil.getUnitNameFromMaterial(material.getUnit()), 20f);
        }
        document.add(ItextUtil.addBookmark("Material", table_material));
    }

    private void setComponentStockReport(Document document, ReportBean reportBean) throws DocumentException {
        PdfStyleBean styleBean = new PdfStyleBean();
        PdfPTable table_component = new PdfPTable(11);
        table_component.setWidthPercentage(106f);
        table_component.setSplitRows(false);
        List<ComponentBean> componentDetails = MisUtil.getComponentBeans(reportBean.getComponents(),
                reportBean.getMaterials(), reportBean.getCategories(), reportBean.getElements_of_components());
        ItextUtil.setMergeCellAlignLeftNoBorder(1, 11, table_component, styleBean.getNormalMoreSmallFont(), 1,
                "Print Time:" + MattUtil.getDateTimeWithUnitWithoutBreak(MattUtil.getDateTime()), 5f);
        ItextUtil.setMergeCellAlignCenter(1, 11, table_component, styleBean.getBoldBigFont(), 5, "Component Stock Report", 20f);

        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Component Code", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Component Name", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Procedure", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Quantity per Procedure", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Duration per Procedure", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 2, table_component, styleBean.getBoldNormalFont(), 5, "Used Material", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 2, table_component, styleBean.getBoldNormalFont(), 5, "Used Component", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Price", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getBoldNormalFont(), 5, "Stock", 20f);

        for(ComponentBean componentBean : componentDetails){
            ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getNormalFont(), 5,
                    componentBean.getIdentifier(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getNormalFont(), 5,
                    componentBean.getName(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getNormalFont(), 5,
                    componentBean.getProcessCategoryName(), 20f);
            if(componentBean.isPurchase()){
                ItextUtil.setMergeCellAlignRight(1, 1, table_component, styleBean.getNormalFont(), 5,
                        "", 20f);
                ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getNormalFont(), 5,
                        "", 20f);
                ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getNormalFont(), 5,
                        "", 20f);
                ItextUtil.setMergeCellAlignRight(1, 1, table_component, styleBean.getNormalFont(), 5,
                        "", 20f);
            }else{
                ItextUtil.setMergeCellAlignRight(1, 1, table_component, styleBean.getNormalFont(), 5,
                        componentBean.getProcess_yield() + "件", 20f);
                ItextUtil.setMergeCellAlignCenter(1, 1, table_component, styleBean.getNormalFont(), 5,
                        componentBean.getTotalTimeBean().getTimeBeanChineseString(), 20f);
                ItextUtil.setMergeCellAlignCenter(1, 2, table_component, styleBean.getNormalFont(), 5,
                        MisUtil.getElementOfComponentStringForReport(reportBean.getComponents(), reportBean.getMaterials(), componentBean.getMaterials()), 20f);
                ItextUtil.setMergeCellAlignRight(1, 2, table_component, styleBean.getNormalFont(), 5,
                        MisUtil.getElementOfComponentStringForReport(reportBean.getComponents(), reportBean.getMaterials(), componentBean.getComponents()), 20f);
            }
            ItextUtil.setMergeCellAlignRight(1, 1, table_component, styleBean.getNormalFont(), 5,
                    MattUtil.transNumberToMoneyNumber(componentBean.getPurchase_fee()) + "NTD", 20f);
            ItextUtil.setMergeCellAlignRight(1, 1, table_component, styleBean.getNormalFont(), 5,
                    String.valueOf(componentBean.getRemain()), 20f);
        }
        document.add(ItextUtil.addBookmark("Component", table_component));
    }

    private void setProductStockReport(Document document, ReportBean reportBean) throws DocumentException {
        PdfStyleBean styleBean = new PdfStyleBean();
        PdfPTable table_product = new PdfPTable(4);
        table_product.setWidthPercentage(106f);
        table_product.setSplitRows(false);
        List<ProductBean> productBeans = MisUtil.getCombinedProducts(reportBean.getProducts(), reportBean.getComponentOfProducts(), reportBean.getComponents());
        ItextUtil.setMergeCellAlignLeftNoBorder(1, 4, table_product, styleBean.getNormalMoreSmallFont(), 1,
                "Print Time:" + MattUtil.getDateTimeWithUnitWithoutBreak(MattUtil.getDateTime()), 5f);
        ItextUtil.setMergeCellAlignCenter(1, 4, table_product, styleBean.getBoldBigFont(), 5, "Product Stock Report", 20f);

        ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getBoldNormalFont(), 5, "Product Code", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getBoldNormalFont(), 5, "Product Name", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getBoldNormalFont(), 5, "Used Component", 20f);
        ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getBoldNormalFont(), 5, "Stock", 20f);
        for(ProductBean productBean : productBeans) {
            String componentsContext = "";
            ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getNormalFont(), 5,
                    productBean.getIdentifier(), 20f);
            ItextUtil.setMergeCellAlignCenter(1, 1, table_product, styleBean.getNormalFont(), 5,
                    productBean.getName(), 20f);
            for(ComponentBean component : productBean.getComponents()){
                for(Component_of_product componentOfProduct : reportBean.getComponentOfProducts()){
                    if(componentOfProduct.getProduct_sn().equals(productBean.getSn())){
                        if(componentOfProduct.getComponent_sn().equals(component.getSn())){
                            componentsContext += component.getName() + "\t" + componentOfProduct.getComponent_amount() + "";
                            if(productBean.getComponents().indexOf(component) != productBean.getComponents().size() - 1){
                                componentsContext += "\n";
                                break;
                            }
                        }
                    }
                }
            }
            ItextUtil.setMergeCellAlignRight(1, 1, table_product, styleBean.getNormalFont(), 5,
                    componentsContext, 20f);
            ItextUtil.setMergeCellAlignRight(1, 1, table_product, styleBean.getNormalFont(), 5,
                    productBean.getRemain() + "件", 20f);
        }
        document.add(ItextUtil.addBookmark("Product", table_product));
    }
}
