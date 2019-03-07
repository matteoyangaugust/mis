package com.matt.service.impl.manage;

import com.matt.bean.*;
import com.matt.model.*;
import com.matt.repository.*;
import com.matt.repository.impl.MaterialOfComponentRepository;
import com.matt.service.BaseService;
import com.matt.service.manage.ManagePrintService;
import com.matt.util.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ManagePrintServiceImpl extends BaseService implements ManagePrintService {

    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MaterialSupplierRepository materialSupplierRepository;
    @Autowired
    private MaterialOfComponentRepository materialOfComponentRepository;
    @Autowired
    private ProcessCategoryRepository processCategoryRepository;
    @Autowired
    private ComponentsOfProductRepository componentsOfProductRepository;
    @Autowired
    private MaterialFlowingHistoryRepository materialFlowingHistoryRepository;
    @Autowired
    private ComponentFlowingHistoryRepository componentFlowingHistoryRepository;
    @Autowired
    private ProductFlowingHistoryRepository productFlowingHistoryRepository;

    private List<Material> materials;
    private List<Component> components;
    private List<Product> products;
    private List<Process_category> categories;
    private List<Material_supplier> suppliers;
    private List<Component_of_product> componentOfProducts;
    private List<Elements_of_component> materialOfComponents;
    private List<Material_flowing_history> material_flowing_histories;
    private List<Component_flowing_history> component_flowing_histories;
    private List<Product_flowing_history> product_flowing_histories;
    private ExcelStyleBean excelStyleBean;
    protected final String mingPath = "C:\\windows\\fonts\\MINGLIU.TTC,1";
    

    @Override
    public HSSFWorkbook getExcel(PrintBean printBean, System_user user) {
        switch (printBean.getReportChoice()){
            case PrintBean.GENERATION_REPORT:
                return getGenerationReport(printBean, user);
            case PrintBean.STOCK_REPORT:
                return getStockReport(printBean);
        }
        return new HSSFWorkbook();
    }

    @Override
    public String getPdfView(PrintBean printBean) {
        if(printBean.getReportChoice().equals(PrintBean.GENERATION_REPORT)){
            return PrintBean.GENERATION_PDF_VIEW;
        }else{
            return PrintBean.STOCK_PDF_VIEW;
        }
    }

    @Override
    public ReportBean getReportData(PrintBean printBean, System_user user){
        if(printBean.getReportChoice().equals(PrintBean.GENERATION_REPORT)){
            return initialGenerationData(user, printBean);
        }else if(printBean.getReportChoice().equals(PrintBean.STOCK_REPORT)){
            return initialStockData();
        }else{
            return new ReportBean();
        }
    }

    private ReportBean initialStockData(){
        materials = materialRepository.findMaterials();
        components = componentRepository.findComponents();
        products = productRepository.findProducts();
        suppliers = materialSupplierRepository.findMaterialSuppliers();
        categories = processCategoryRepository.findProcessCategories();
        componentOfProducts = componentsOfProductRepository.findComponentOdProducts();
        materialOfComponents = materialOfComponentRepository.findMaterialsOfComponent();
        ListUtil.sort(materials, "identifier");
        ListUtil.sort(components, "identifier");
        ListUtil.sort(products, "identifier");
        ListUtil.sort(suppliers, "name");
        ListUtil.sort(categories, "sn");
        return getReportBean();
    }

    private ReportBean getReportBean(){
        ReportBean reportBean = new ReportBean();
        reportBean.setCategories(this.categories);
        reportBean.setComponent_flowing_histories(this.component_flowing_histories);
        reportBean.setElements_of_components(this.materialOfComponents);
        reportBean.setComponentOfProducts(this.componentOfProducts);
        reportBean.setComponents(this.components);
        reportBean.setMaterial_flowing_histories(this.material_flowing_histories);
        reportBean.setMaterials(this.materials);
        reportBean.setProduct_flowing_histories(this.product_flowing_histories);
        reportBean.setProducts(this.products);
        reportBean.setSuppliers(this.suppliers);
        return reportBean;
    }

    private ReportBean initialGenerationData(System_user user, PrintBean printBean){
        initialStockData();
        material_flowing_histories = materialFlowingHistoryRepository.findMaterialFlowingHistories();
        component_flowing_histories = componentFlowingHistoryRepository.findFlowingHistories(user);
        product_flowing_histories = productFlowingHistoryRepository.findHistories();
        ListUtil.sort(material_flowing_histories, "sn");
        ListUtil.sort(component_flowing_histories, "sn");
        ListUtil.sort(product_flowing_histories, "sn");
        printBean.setBeginDate(printBean.getBeginDate().replaceAll("-", ""));
        printBean.setEndDate(printBean.getEndDate().replaceAll("-", ""));
        Iterator<Material_flowing_history> mItr = material_flowing_histories.iterator();
        while(mItr.hasNext()){
            Material_flowing_history history = mItr.next();
            if(Integer.parseInt(history.getBuying_time()) < Integer.parseInt(printBean.getBeginDate()) ||
                    Integer.parseInt(history.getBuying_time()) > Integer.parseInt(printBean.getEndDate())){
                mItr.remove();
            }
        }
        Iterator<Component_flowing_history> cItr = component_flowing_histories.iterator();
        while(cItr.hasNext()){
            Component_flowing_history history = cItr.next();
            if(Integer.parseInt(history.getUpdate_time().substring(0, 8)) < Integer.parseInt(printBean.getBeginDate()) ||
                    Integer.parseInt(history.getUpdate_time().substring(0, 8)) > Integer.parseInt(printBean.getEndDate())){
                cItr.remove();
            }
        }
        Iterator<Product_flowing_history> pItr = product_flowing_histories.iterator();
        while(pItr.hasNext()){
            Product_flowing_history history = pItr.next();
            if(Integer.parseInt(history.getUpdate_time().substring(0, 8)) < Integer.parseInt(printBean.getBeginDate()) ||
                    Integer.parseInt(history.getUpdate_time().substring(0, 8)) > Integer.parseInt(printBean.getEndDate())){
                pItr.remove();
            }
        }
        return getReportBean();
    }

    private HSSFWorkbook getStockReport(PrintBean printBean) {
        initialStockData();
        HSSFWorkbook workbook = new HSSFWorkbook();
        excelStyleBean = new ExcelStyleBean(workbook);
        setMaterialStockSheet(workbook);
        setComponentStockSheet(workbook);
        setProductStockSheet(workbook);
        return workbook;
    }

    private void setProductStockSheet(HSSFWorkbook workbook) {
        List<ProductBean> productBeans = MisUtil.getCombinedProducts(products, componentOfProducts, components);
        HSSFSheet sheet = POIUtil.getNewSheet(workbook, "Product", 100);
        //開始製圖
        sheet.createRow(1).setHeight((short)600);
        for(int i=0; i<4; i++){
            sheet.setColumnWidth(i, 6000);
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0,0,3));
        POIUtil.setCellStyle(sheet, 0, 3, 0, 0, excelStyleBean.getDatetimeCellStyle());
        POIUtil.setText(sheet,excelStyleBean.getDatetimeCellStyle(),0,0, "Print Time:" + MattUtil.getDateTimeWithUnitWithoutBreak(MattUtil.getDateTime()));

        sheet.addMergedRegion(new CellRangeAddress(1, 1,0,3));
        POIUtil.setCellStyle(sheet, 0, 3, 1, 1, excelStyleBean.getTitleCellStyle());
        POIUtil.setText(sheet, excelStyleBean.getTitleCellStyle(), 1, 0, "Product Stock Report");

        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 0, "Product Code");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 1, "Product Name");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 2, "Used Component");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 3, "Stock");
        for(ProductBean productBean : productBeans){
            String componentsContext = "";
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), productBeans.indexOf(productBean) + 3, 0, productBean.getIdentifier());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), productBeans.indexOf(productBean) + 3, 1, productBean.getName());
            for(ComponentBean component : productBean.getComponents()){
                for(Component_of_product componentOfProduct : componentOfProducts){
                    if(componentOfProduct.getProduct_sn().equals(productBean.getSn())){
                        if(componentOfProduct.getComponent_sn().equals(component.getSn())){
                            componentsContext += component.getName() + "\t" + componentOfProduct.getComponent_amount() + "";
                            if(productBean.getComponents().indexOf(component) != productBean.getComponents().size() - 1){
                                componentsContext += "\n";
                            }
                            break;
                        }
                    }
                }
            }
            POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), productBeans.indexOf(productBean) + 3, 2, componentsContext);
            POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), productBeans.indexOf(productBean) + 3, 3, productBean.getRemain() + "");
        }
    }

    private void setComponentStockSheet(HSSFWorkbook workbook) {
        List<ComponentBean> componentDetails = MisUtil.getComponentBeans(components, materials, categories, materialOfComponents);
        HSSFSheet sheet = POIUtil.getNewSheet(workbook, "Component", 100);

        //開始製圖
        sheet.createRow(1).setHeight((short)600);
        for(int i=0; i<9; i++){
            if(i == 4){
                sheet.setColumnWidth(i, 5000);
            }else if(i == 5 || i == 6){
                sheet.setColumnWidth(i, 7000);
            }else{
                sheet.setColumnWidth(i, 4000);
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0,0,8));
        POIUtil.setCellStyle(sheet, 0, 8, 0, 0, excelStyleBean.getDatetimeCellStyle());
        POIUtil.setText(sheet,excelStyleBean.getDatetimeCellStyle(),0,0, "Print Time:" + MattUtil.getDateTimeWithUnitWithoutBreak(MattUtil.getDateTime()));

        sheet.addMergedRegion(new CellRangeAddress(1, 1,0,8));
        POIUtil.setCellStyle(sheet, 0, 8, 1, 1, excelStyleBean.getTitleCellStyle());
        POIUtil.setText(sheet, excelStyleBean.getTitleCellStyle(), 1, 0, "Component Stock Report");

        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 0, "Component Code");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 1, "Component Name");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 2, "Procedure");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 3, "Quantity per Procedure");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 4, "Duration per Procedure");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 5, "Used Material");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 6, "Used Component");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 7, "Price");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 8, "Stock");

        for(ComponentBean componentBean : componentDetails){
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), componentDetails.indexOf(componentBean) + 3, 0, componentBean.getIdentifier());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), componentDetails.indexOf(componentBean) + 3, 1, componentBean.getName());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), componentDetails.indexOf(componentBean) + 3, 2, componentBean.getProcessCategoryName());
            if(componentBean.isPurchase()){
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), componentDetails.indexOf(componentBean) + 3, 3, "");
            }else{
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), componentDetails.indexOf(componentBean) + 3, 3, componentBean.getProcess_yield() + "");
            }
            if(componentBean.isPurchase()){
                POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), componentDetails.indexOf(componentBean) + 3, 4, "");
            }else{
                POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), componentDetails.indexOf(componentBean) + 3, 4, componentBean.getTotalTimeBean().getTimeBeanChineseString());
            }
            if(componentBean.isPurchase()){
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), componentDetails.indexOf(componentBean) + 3, 6, "");
            }else{
                POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), componentDetails.indexOf(componentBean) + 3, 5, MisUtil.getElementOfComponentStringForReport(components, materials, componentBean.getMaterials()));
                POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), componentDetails.indexOf(componentBean) + 3, 6, MisUtil.getElementOfComponentStringForReport(components, materials, componentBean.getComponents()));
            }
            POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), componentDetails.indexOf(componentBean) + 3, 7, MattUtil.transNumberToMoneyNumber(componentBean.getPurchase_fee()) + " NTD");
            POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), componentDetails.indexOf(componentBean) + 3, 8, String.valueOf(componentBean.getRemain()));
        }
    }

    private void setMaterialStockSheet(HSSFWorkbook workbook) {
        HSSFSheet sheet = POIUtil.getNewSheet(workbook, "Material", 100);
        //開始製圖
        sheet.createRow(1).setHeight((short)600);
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);

        sheet.addMergedRegion(new CellRangeAddress(0, 0,0,4));
        POIUtil.setCellStyle(sheet, 0, 4, 0, 0, excelStyleBean.getDatetimeCellStyle());
        POIUtil.setText(sheet,excelStyleBean.getDatetimeCellStyle(),0,0, "Print Time:" + MattUtil.getDateTimeWithUnitWithoutBreak(MattUtil.getDateTime()));

        sheet.addMergedRegion(new CellRangeAddress(1, 1,0,4));
        POIUtil.setCellStyle(sheet, 0, 4, 1, 1, excelStyleBean.getTitleCellStyle());
        POIUtil.setText(sheet, excelStyleBean.getTitleCellStyle(), 1, 0, "Material Stock Report");

        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 0, "Material Code");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 1, "Material Name");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 2, "Supplier");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 3, "Price");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 4, "Stock");

        for(Material material : materials){
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), materials.indexOf(material) + 3, 0, material.getIdentifier());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), materials.indexOf(material) + 3, 1, material.getName());
            for(Material_supplier supplier : suppliers){
                if(material.getSupplier().equals(supplier.getSn())){
                    POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), materials.indexOf(material) + 3, 2, supplier.getName());
                }
            }
            POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), materials.indexOf(material) + 3, 3, MattUtil.transNumberToMoneyNumber(material.getPrice()) + " NTD");
            POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), materials.indexOf(material) + 3, 4,
                    (material.getQuantity() == material.getQuantity().intValue() ?
                            material.getQuantity().intValue() : material.getQuantity()) +
                            MisUtil.getUnitNameFromMaterial(material.getUnit()));
        }
    }

    private HSSFWorkbook getGenerationReport(PrintBean printBean, System_user user) {
        initialGenerationData(user, printBean);
        HSSFWorkbook workbook = new HSSFWorkbook();
        excelStyleBean = new ExcelStyleBean(workbook);
        setMaterialGenerationSheet(workbook);
        setComponentGenerationSheet(workbook);
        setProductGenerationSheet(workbook);
        return workbook;
    }

    private void setProductGenerationSheet(HSSFWorkbook workbook) {
        HSSFSheet sheet = POIUtil.getNewSheet(workbook, "Build Product Report", 100);
        List<ProductFlowingHistoryBean> histories = MisUtil.getCombinedProductFlowingHistories(components, componentOfProducts, products, product_flowing_histories);
        ListUtil.sort(histories, "product_sn", "sn");
        //開始製圖
        sheet.createRow(1).setHeight((short)600);
        for(int i=0; i<6; i++){
            sheet.setColumnWidth(i, 6000);
        }
        //Print Time
        sheet.addMergedRegion(new CellRangeAddress(0, 0,0,6));
        POIUtil.setCellStyle(sheet, 0, 6, 0, 0, excelStyleBean.getDatetimeCellStyle());
        POIUtil.setText(sheet,excelStyleBean.getDatetimeCellStyle(),0,0, "Print Time:" + MattUtil.getDateTimeWithUnitWithoutBreak(MattUtil.getDateTime()));
        //Title
        sheet.addMergedRegion(new CellRangeAddress(1, 1,0,6));
        POIUtil.setCellStyle(sheet, 0, 6, 1, 1, excelStyleBean.getTitleCellStyle());
        POIUtil.setText(sheet, excelStyleBean.getTitleCellStyle(), 1, 0, "Build Product Report");

        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 0, "Build/Sell Time");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 1, "Action");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 2, "Product Code");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 3, "Product Name");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 4, "Used Component");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 5, "Stock");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 6, "Quantity");
        int total = 0;
        int subtotal = 0;
        int subtotalCounter = 0;
        int prevProductSn = 0;
        for(ProductFlowingHistoryBean history : histories){
            if(!history.getProduct_sn().equals(prevProductSn) && histories.indexOf(history) != 0){
                sheet.addMergedRegion(new CellRangeAddress(histories.indexOf(history) + 3 + subtotalCounter, histories.indexOf(history) + 3 + subtotalCounter,0,5));
                POIUtil.setCellStyle(sheet, 0, 5, histories.indexOf(history) + 3 + subtotalCounter, histories.indexOf(history) + 3 + subtotalCounter, excelStyleBean.getTitleCellStyle());
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subtotalCounter, 0, "Subtotal");
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subtotalCounter, 6, subtotal + "");
                subtotalCounter++;
                //Subtotal歸0
                subtotal = 0;
            }
            prevProductSn = history.getProduct_sn();
            //一般資料
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subtotalCounter, 0,
                    history.getUpdateTimeWithUnit());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subtotalCounter, 1,
                    history.getActionString());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subtotalCounter, 2,
                    history.getProduct().getIdentifier());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subtotalCounter, 3,
                    history.getProduct().getName());
            if(history.getAction().equals(Product_flowing_history.ACTION_SELL)){
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subtotalCounter, 4,
                        "");
            }else{
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subtotalCounter, 4,
                        MisUtil.getComponentListReportString(componentOfProducts, components, history));
            }
            POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subtotalCounter, 5,
                    history.getRemain() + "");
            POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subtotalCounter, 6,
                    history.getAmount() + "");
            subtotal += history.getAmount();
            total += history.getAmount();
            if(histories.indexOf(history) == histories.size() - 1){
                subtotalCounter++;
                sheet.addMergedRegion(new CellRangeAddress(histories.indexOf(history) + 3 + subtotalCounter, histories.indexOf(history) + 3 + subtotalCounter,0,5));
                POIUtil.setCellStyle(sheet, 0, 5, histories.indexOf(history) + 3 + subtotalCounter, histories.indexOf(history) + 3 + subtotalCounter, excelStyleBean.getTitleCellStyle());
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subtotalCounter, 0, "Subtotal");
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subtotalCounter, 6, subtotal + "");
            }
        }
        //Total
        sheet.addMergedRegion(new CellRangeAddress(histories.size() + 3 + subtotalCounter, histories.size() + 3 + subtotalCounter,0,5));
        POIUtil.setCellStyle(sheet, 0, 5, histories.size() + 3 + subtotalCounter, histories.size() + 3 + subtotalCounter, excelStyleBean.getContentRightCellStyleWithoutBorder());
        POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyleWithoutBorder(), histories.size() + 3 + subtotalCounter, 0, "Total：");
        POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyleWithoutBorder(), histories.size() + 3 + subtotalCounter, 6, total + "");

    }

    private void setComponentGenerationSheet(HSSFWorkbook workbook) {
        HSSFSheet sheet = POIUtil.getNewSheet(workbook, "Build Component Report", 100);
        List<ComponentFlowingHistoryBean> histories = MisUtil.getCombinedComponentFlowingHistories(component_flowing_histories, components, materials, categories, materialOfComponents);
        ListUtil.sort(histories, "component_sn", "sn");
        //開始製圖
        sheet.createRow(1).setHeight((short)600);
        for(int i=0; i<11; i++){
            sheet.setColumnWidth(i, 5000);
        }
        //Print Time
        sheet.addMergedRegion(new CellRangeAddress(0, 0,0,10));
        POIUtil.setCellStyle(sheet, 0, 10, 0, 0, excelStyleBean.getDatetimeCellStyle());
        POIUtil.setText(sheet,excelStyleBean.getDatetimeCellStyle(),0,0, "Print Time:" + MattUtil.getDateTimeWithUnitWithoutBreak(MattUtil.getDateTime()));
        //Title
        sheet.addMergedRegion(new CellRangeAddress(1, 1,0,10));
        POIUtil.setCellStyle(sheet, 0, 10, 1, 1, excelStyleBean.getTitleCellStyle());
        POIUtil.setText(sheet, excelStyleBean.getTitleCellStyle(), 1, 0, "Build Component Report");

        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 0, "Build Time");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 1, "Component Code");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 2, "Component Name");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 3, "Procedure");
        sheet.addMergedRegion(new CellRangeAddress(2, 2,4,5));
        POIUtil.setCellStyle(sheet, 4, 5, 2, 2, excelStyleBean.getItemNameCellStyle());
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 4, "Used Material");
//        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 5, "使用原料量");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 6, "Amount of Procedure");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 7, "Quantity of Procedure");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 8, "Quantity");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 9, "Stock");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 10, "Price");
        //Total的價錢
        Double totalFee = 0.0;
        //前一個history的component_sn
        int prevComponentSn = 0;
        //Subtotal的價錢
        Double subTotalFee = 0.0;
        //有幾個Subtotal列
        int subTotalCounter = 0;
        for(ComponentFlowingHistoryBean history : histories){
            if(!history.getComponent_sn().equals(prevComponentSn) && histories.indexOf(history) != 0){
                sheet.addMergedRegion(new CellRangeAddress(histories.indexOf(history) + 3 + subTotalCounter, histories.indexOf(history) + 3 + subTotalCounter,0,9));
                POIUtil.setCellStyle(sheet, 0, 9, histories.indexOf(history) + 3 + subTotalCounter, histories.indexOf(history) + 3 + subTotalCounter, excelStyleBean.getTitleCellStyle());
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 0, "Subtotal");
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 10, MattUtil.transNumberToMoneyNumber(subTotalFee) + " NTD");
                subTotalCounter++;
                //Subtotal歸0
                subTotalFee = 0.0;
            }
            prevComponentSn = history.getComponent_sn();
            //一般資料
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 0,
                    history.getUpdateTimeWithUnit());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 1,
                    history.getComponent().getIdentifier());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 2,
                    history.getComponent().getName());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 3,
                    history.getProcessCategory().getName());
            sheet.addMergedRegion(new CellRangeAddress(histories.indexOf(history) + 3 + subTotalCounter, histories.indexOf(history) + 3 + subTotalCounter,4,5));
            POIUtil.setCellStyle(sheet, 4, 5, histories.indexOf(history) + 3 + subTotalCounter, histories.indexOf(history) + 3 + subTotalCounter, excelStyleBean.getItemNameCellStyle());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 4,
                    history.getProcessCategory().getRelation().contains("ROLE_PURCHASE") ? "" : MisUtil.getMaterialOfComponentStringForReport(history));
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 6,
                    history.getProcessCategory().getRelation().contains("ROLE_PURCHASE") ? "" : String.valueOf(history.getProcess_amount()));
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 7,
                    history.getProcessCategory().getRelation().contains("ROLE_PURCHASE") ? "" : String.valueOf(history.getComponent().getProcess_yield()));
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 8,
                    String.valueOf(history.getAmount()));
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 9,
                    String.valueOf(history.getRemain()));
            POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 10,
                    MattUtil.transNumberToMoneyNumber(history.getPurchase_fee()) + " NTD");


            //Total累加
            totalFee = DoubleUtil.add(totalFee, history.getPurchase_fee());
            //Subtotal累加
            subTotalFee = DoubleUtil.add(subTotalFee, history.getPurchase_fee());
            //最後一次Subtotal
            if(histories.indexOf(history) == histories.size() - 1){
                subTotalCounter++;
                sheet.addMergedRegion(new CellRangeAddress(histories.indexOf(history) + 3 + subTotalCounter, histories.indexOf(history) + 3 + subTotalCounter,0,9));
                POIUtil.setCellStyle(sheet, 0, 9, histories.indexOf(history) + 3 + subTotalCounter, histories.indexOf(history) + 3 + subTotalCounter, excelStyleBean.getTitleCellStyle());
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 0, "Subtotal");
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 10, MattUtil.transNumberToMoneyNumber(subTotalFee) + " NTD");
            }
        }
        //Total
        sheet.addMergedRegion(new CellRangeAddress(histories.size() + 3 + subTotalCounter, histories.size() + 3 + subTotalCounter,0,9));
        POIUtil.setCellStyle(sheet, 0, 9, histories.size() + 3 + subTotalCounter, histories.size() + 3 + subTotalCounter, excelStyleBean.getContentRightCellStyleWithoutBorder());
        POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyleWithoutBorder(), histories.size() + 3 + subTotalCounter, 0, "Total：");
        POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyleWithoutBorder(), histories.size() + 3 + subTotalCounter, 10, MattUtil.transNumberToMoneyNumber(totalFee) + " NTD");

    }

    private void setMaterialGenerationSheet(HSSFWorkbook workbook) {
        HSSFSheet sheet = POIUtil.getNewSheet(workbook, "Buy Material Report", 100);
        List<MaterialFlowingHistoryBean> histories = MisUtil.getCombinedMaterialFlowingHistories(material_flowing_histories, materials, suppliers);
        //開始製圖
        sheet.createRow(1).setHeight((short)600);
        for(int i=0; i<8; i++){
            sheet.setColumnWidth(i, 5000);
        }
        //Print Time
        sheet.addMergedRegion(new CellRangeAddress(0, 0,0,7));
        POIUtil.setCellStyle(sheet, 0, 7, 0, 0, excelStyleBean.getDatetimeCellStyle());
        POIUtil.setText(sheet,excelStyleBean.getDatetimeCellStyle(),0,0, "Print Time:" + MattUtil.getDateTimeWithUnitWithoutBreak(MattUtil.getDateTime()));
        //Title
        sheet.addMergedRegion(new CellRangeAddress(1, 1,0,7));
        POIUtil.setCellStyle(sheet, 0, 7, 1, 1, excelStyleBean.getTitleCellStyle());
        POIUtil.setText(sheet, excelStyleBean.getTitleCellStyle(), 1, 0, "But Material Report");

        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 0, "Update Time");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 1, "Buying Time");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 2, "Material Code");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 3, "Material Name");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 4, "Supplier");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 5, "Quantity");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 6, "Stock");
        POIUtil.setText(sheet, excelStyleBean.getItemNameCellStyle(), 2, 7, "Price");
        ListUtil.sort(histories, "material_sn", "buying_time");
        //Total的價錢
        Double totalFee = 0.0;
        //前一個history的Material_sn
        int prevMaterialSn = 0;
        //Subtotal的價錢
        Double subTotalFee = 0.0;
        //有幾個Subtotal列
        int subTotalCounter = 0;
        for(MaterialFlowingHistoryBean history : histories){
            if(!history.getMaterial_sn().equals(prevMaterialSn) && histories.indexOf(history) != 0){
                sheet.addMergedRegion(new CellRangeAddress(histories.indexOf(history) + 3 + subTotalCounter, histories.indexOf(history) + 3 + subTotalCounter,0,6));
                POIUtil.setCellStyle(sheet, 0, 6, histories.indexOf(history) + 3 + subTotalCounter, histories.indexOf(history) + 3 + subTotalCounter, excelStyleBean.getTitleCellStyle());
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 0, "Subtotal");
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 7, MattUtil.transNumberToMoneyNumber(subTotalFee) + " NTD");
                subTotalCounter++;
                //Subtotal歸0
                subTotalFee = 0.0;
            }
            prevMaterialSn = history.getMaterial_sn();
            //一般資料
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 0,
                    history.getUpdateTimeWithUnit());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 1,
                    history.getBuyingTimeWithUnit());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 2,
                    history.getMaterialIdentifier());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 3,
                    history.getMaterialName());
            POIUtil.setText(sheet, excelStyleBean.getContentCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 4,
                    history.getSupplierName());
            POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 5,
                    String.valueOf(history.getQuantity() == history.getQuantity().intValue() ? history.getQuantity().intValue() : history.getQuantity()) + history.getUnitName());
            POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 6,
                    String.valueOf(history.getRemain() == history.getRemain().intValue() ? history.getRemain().intValue() : history.getRemain()) + history.getUnitName());
            POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 7,
                    MattUtil.transNumberToMoneyNumber(history.getFee()) + " NTD");
            //Total累加
            totalFee = DoubleUtil.add(totalFee, history.getFee());
            //Subtotal累加
            subTotalFee = DoubleUtil.add(subTotalFee, history.getFee());
            //最後一次Subtotal
            if(histories.indexOf(history) == histories.size() - 1){
                subTotalCounter++;
                sheet.addMergedRegion(new CellRangeAddress(histories.indexOf(history) + 3 + subTotalCounter, histories.indexOf(history) + 3 + subTotalCounter,0,6));
                POIUtil.setCellStyle(sheet, 0, 6, histories.indexOf(history) + 3 + subTotalCounter, histories.indexOf(history) + 3 + subTotalCounter, excelStyleBean.getTitleCellStyle());
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 0, "Subtotal");
                POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyle(), histories.indexOf(history) + 3 + subTotalCounter, 7, MattUtil.transNumberToMoneyNumber(subTotalFee) + " NTD");
            }
        }
        //Total
        sheet.addMergedRegion(new CellRangeAddress(histories.size() + 3 + subTotalCounter, histories.size() + 3 + subTotalCounter,0,6));
        POIUtil.setCellStyle(sheet, 0, 6, histories.size() + 3 + subTotalCounter, histories.size() + 3 + subTotalCounter, excelStyleBean.getContentRightCellStyleWithoutBorder());
        POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyleWithoutBorder(), histories.size() + 3 + subTotalCounter, 0, "Total：");
        POIUtil.setText(sheet, excelStyleBean.getContentRightCellStyleWithoutBorder(), histories.size() + 3 + subTotalCounter, 7, MattUtil.transNumberToMoneyNumber(totalFee) + " NTD");
    }


}
