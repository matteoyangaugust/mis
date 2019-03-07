package com.matt.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class POIUtil {

    public static void outputExcel(HttpServletResponse response, String fileName, HSSFWorkbook workbook) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream os = response.getOutputStream();
        workbook.write(os);
        os.flush();
        os.close();
    }

    public static HSSFSheet getNewSheet(HSSFWorkbook workbook, String sheetName, int scale){
        HSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.getPrintSetup().setVResolution((short) 600);
        sheet.getPrintSetup().setLandscape(true);
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setFitHeight((short)0);
        sheet.getPrintSetup().setFitWidth((short)1);
        sheet.getPrintSetup().setScale((short)scale);
        sheet.setMargin(HSSFSheet.LeftMargin, 0.1);
        sheet.setMargin(HSSFSheet.RightMargin, 0.1);
        sheet.getFooter().setCenter(HSSFFooter.page()+ "/" + HSSFFooter.numPages());
        sheet.setHorizontallyCenter(true);
        sheet.setAutobreaks(true);
        return sheet;
    }

	public static void setText(HSSFSheet sheet, int row, int col, String text) {
		HSSFCell cell = getCell(sheet, row, col);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(StringEscapeUtils.unescapeHtml(text));
	}
	
	public static void setText(HSSFSheet sheet, HSSFCellStyle style, int row, int col, String text) {
		HSSFCell cell = getCell(sheet, row, col);
		cell.setCellStyle(style);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(StringEscapeUtils.unescapeHtml(text));
	}
	
	public static HSSFCell getCell(HSSFSheet sheet, int row, int col) {
		HSSFRow sheetRow = sheet.getRow(row);
		if (sheetRow == null) {
			sheetRow = sheet.createRow(row);
		}
		HSSFCell cell = sheetRow.getCell((short) col);
		if (cell == null) {
			cell = sheetRow.createCell((short) col);
		}
		return cell;
	}

    public static void setCellStyle(HSSFSheet sheet, int beginCol, int endCol, int beginRow, int lastRow, HSSFCellStyle style){
        for(int i=beginRow; i<=lastRow; i++){
            for(int j=beginCol; j<=endCol; j++){
                POIUtil.setText(sheet, style, i, j, "");
            }
        }
    }
	
	/**
	 * Create Excel Cell with style
	 * @param workbook
	 * @param isBorder 是否需要框線
	 * @return
	 */
	public static HSSFCellStyle getStyle(HSSFWorkbook workbook, boolean isBorder)
	{
		HSSFCellStyle style = workbook.createCellStyle();
		if(isBorder)
		{
			style.setBorderBottom((short) 1);
			style.setBorderLeft((short) 1);
			style.setBorderRight((short) 1);
			style.setBorderTop((short) 1);
		}
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setWrapText(true);
		return style;
	}

    /**
     * Create Excel Cell with style
     * @param workbook
     * @param isBorder 是否需要框線
     * @return
     */
    public static HSSFCellStyle getStyle(HSSFWorkbook workbook, boolean isBorder, short align, short vertical)
    {
        HSSFCellStyle style = workbook.createCellStyle();
        if(isBorder)
        {
            style.setBorderBottom((short) 1);
            style.setBorderLeft((short) 1);
            style.setBorderRight((short) 1);
            style.setBorderTop((short) 1);
        }
        style.setAlignment(align);
        style.setVerticalAlignment(vertical);
        style.setWrapText(true);
        return style;
    }
	
	/**
	 * 設定文字
	 * @param workBook
	 * @param fontStyle	字體
	 * @param fontSize	字的大小
	 * @return
	 */
	public static HSSFFont setFont(HSSFWorkbook workBook, String fontStyle, short fontSize, boolean isBold) {
		HSSFFont font = workBook.createFont();
		font.setFontName(fontStyle);                                    
		font.setFontHeightInPoints(fontSize);
		font.setBold(isBold);
		return font;                             
	}
	
	public static void copyRowMergedRegion(HSSFSheet sheet, HSSFRow fromRow, HSSFRow toRow){
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
	        CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
	        if (cellRangeAddress.getFirstRow() == fromRow.getRowNum()) {
	            CellRangeAddress newCellRangeAddress =
	                  new CellRangeAddress(toRow.getRowNum(),
	                    (toRow.getRowNum() +
	                    (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())
	                     ),
	                    cellRangeAddress.getFirstColumn(),
	                    cellRangeAddress.getLastColumn());
	            sheet.addMergedRegion(newCellRangeAddress);
	        }
	    }
	}
	
//	public static void copyRow(HSSFWorkbook wb, HSSFSheet sheet, int from, int to, boolean copyValueFlag){
//		HSSFRow fromRow = sheet.getRow(from);
//		HSSFRow toRow = sheet.createRow(to);
//		copyRowMergedRegion(sheet,fromRow,toRow);
//		for (Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext();) {
//            HSSFCell tmpCell = (HSSFCell) cellIt.next();
//            HSSFCell newCell = toRow.createCell(tmpCell.getCellNum());
//            toRow.setHeight(fromRow.getHeight());
//            copyCell(wb,tmpCell, newCell, copyValueFlag);
//        }
//    }
	
	public static void copyCell(HSSFWorkbook wb, HSSFCell srcCell, HSSFCell distCell,
                                boolean copyValueFlag) {
        HSSFCellStyle newstyle=wb.createCellStyle();
        copyCellStyle(wb,srcCell.getCellStyle(), newstyle);  
        distCell.setCellStyle(newstyle);  
        if (srcCell.getCellComment() != null) {  
            distCell.setCellComment(srcCell.getCellComment());  
        }  
        int srcCellType = srcCell.getCellType();  
        distCell.setCellType(srcCellType);  
        if (copyValueFlag) {  
            if (srcCellType == HSSFCell.CELL_TYPE_NUMERIC) {
                if (HSSFDateUtil.isCellDateFormatted(srcCell)) {
                    distCell.setCellValue(srcCell.getDateCellValue());  
                } else {  
                    distCell.setCellValue(srcCell.getNumericCellValue());  
                }  
            } else if (srcCellType == HSSFCell.CELL_TYPE_STRING) {
                distCell.setCellValue(srcCell.getRichStringCellValue());  
            } else if (srcCellType == HSSFCell.CELL_TYPE_BLANK) {
                // nothing21  
            } else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {
                distCell.setCellValue(srcCell.getBooleanCellValue());  
            } else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {
                distCell.setCellErrorValue(srcCell.getErrorCellValue());  
            } else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {
                distCell.setCellFormula(srcCell.getCellFormula());  
            } else { // nothing29  
            }  
        }  
    }
	
	public static void copyCellStyle(HSSFWorkbook workbook, HSSFCellStyle fromStyle,
                                     HSSFCellStyle toStyle) {
        toStyle.setAlignment(fromStyle.getAlignment());  

        toStyle.setBorderBottom(fromStyle.getBorderBottom());  
        toStyle.setBorderLeft(fromStyle.getBorderLeft());  
        toStyle.setBorderRight(fromStyle.getBorderRight());  
        toStyle.setBorderTop(fromStyle.getBorderTop());  
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());  
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());  
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());  
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());  
          
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());  
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());  
          
        toStyle.setDataFormat(fromStyle.getDataFormat());  
        toStyle.setFillPattern(fromStyle.getFillPattern());  
        toStyle.setFont(fromStyle.getFont(workbook));
        toStyle.setHidden(fromStyle.getHidden());  
        toStyle.setIndention(fromStyle.getIndention());
        toStyle.setLocked(fromStyle.getLocked());  
        toStyle.setRotation(fromStyle.getRotation()); 
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());  
        toStyle.setWrapText(fromStyle.getWrapText());
    }

    /**
     * 功能：拷貝sheet
     * @param targetSheet
     * @param sourceSheet
     * @param targetWork
     * @param sourceWork
     */
    public static void copySheet(HSSFSheet targetSheet, HSSFSheet sourceSheet,
                                 HSSFWorkbook targetWork, HSSFWorkbook sourceWork)throws Exception {

        if(targetSheet == null || sourceSheet == null || targetWork == null || sourceWork == null){
            throw new IllegalArgumentException("調用PoiUtil.copySheet()方法時，targetSheet、sourceSheet、targetWork、sourceWork都不能為空，故拋出該異常！");
        }

        targetSheet.setMargin(HSSFSheet.TopMargin, sourceSheet.getMargin(HSSFSheet.TopMargin));
        targetSheet.setMargin(HSSFSheet.BottomMargin, sourceSheet.getMargin(HSSFSheet.BottomMargin));
        targetSheet.setMargin(HSSFSheet.LeftMargin, sourceSheet.getMargin(HSSFSheet.LeftMargin));
        targetSheet.setMargin(HSSFSheet.RightMargin, sourceSheet.getMargin(HSSFSheet.RightMargin));

        //複製源表中的行
        int maxColumnNum = 0;

        HSSFPatriarch patriarch = targetSheet.createDrawingPatriarch(); //用於複製注釋
        for (int i = sourceSheet.getFirstRowNum(); i <= sourceSheet.getLastRowNum(); i++) {

            HSSFRow sourceRow = sourceSheet.getRow(i);
            HSSFRow targetRow = targetSheet.createRow(i);

            if (sourceRow != null) {
                copyRow(targetRow, sourceRow,
                        targetWork, sourceWork,patriarch,targetWork.createCellStyle());
                if (sourceRow.getLastCellNum() > maxColumnNum) {
                    maxColumnNum = sourceRow.getLastCellNum();
                }
            }
        }

        //複製源表中的合併儲存格
        mergerRegion(targetSheet, sourceSheet);

        //設置目標sheet的列寬
        for (int i = 0; i <= maxColumnNum; i++) {
            targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
        }
    }
    
    /**
     * 功能：拷貝sheet 可複製style
     * @param targetSheet
     * @param sourceSheet
     * @param targetWork
     * @param sourceWork
     * @param copyStyle
     * @throws Exception
     */
    public static void copySheet(HSSFSheet targetSheet, HSSFSheet sourceSheet,
                                 HSSFWorkbook targetWork, HSSFWorkbook sourceWork, boolean copyStyle)throws Exception {
		
		if(targetSheet == null || sourceSheet == null || targetWork == null || sourceWork == null){
			throw new IllegalArgumentException("調用PoiUtil.copySheet()方法時，targetSheet、sourceSheet、targetWork、sourceWork都不能為空，故拋出該異常！");
		}
		
		//複製源表中的行
		int maxColumnNum = 0;

		Map<String,HSSFCellStyle> styleMap = (copyStyle) ? new HashMap<String,HSSFCellStyle>() : null;
		
		HSSFPatriarch patriarch = targetSheet.createDrawingPatriarch(); //用於複製註釋
		for (int i = sourceSheet.getFirstRowNum(); i <= sourceSheet.getLastRowNum(); i++) {
			HSSFRow sourceRow = sourceSheet.getRow(i);
			HSSFRow targetRow = targetSheet.createRow(i);
			
			if (sourceRow != null) {
				copyRow(targetRow, sourceRow,
						targetWork, sourceWork,patriarch, styleMap);
				if (sourceRow.getLastCellNum() > maxColumnNum) {
					maxColumnNum = sourceRow.getLastCellNum();
				}
			}
		}
		
		//複製源表中的合併的格
		mergerRegion(targetSheet, sourceSheet);
		
		//設置目標sheet的列寬
		for (int i = 0; i <= maxColumnNum; i++) {
			targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
		}
	}
    /**
     * 拷貝Row
     * @param targetRow
     * @param sourceRow
     * @param targetWork
     * @param sourceWork
     * @param targetPatriarch
     * @param styleMap
     * @throws Exception
     */
    public static void copyRow(HSSFRow targetRow, HSSFRow sourceRow,
                               HSSFWorkbook targetWork, HSSFWorkbook sourceWork, HSSFPatriarch targetPatriarch, Map<String,HSSFCellStyle> styleMap) throws Exception {
		if(targetRow == null || sourceRow == null || targetWork == null || sourceWork == null || targetPatriarch == null){
			throw new IllegalArgumentException("調用PoiUtil.copyRow()方法時，targetRow、sourceRow、targetWork、sourceWork、targetPatriarch都不能為空，故拋出該異常！");
		}
		
		//設置行高
		targetRow.setHeight(sourceRow.getHeight());
		
		for (int i = sourceRow.getFirstCellNum(); i <= sourceRow.getLastCellNum(); i++) {
			HSSFCell sourceCell = sourceRow.getCell(i);
			HSSFCell targetCell = targetRow.getCell(i);
			
			if (sourceCell != null) {
				if (targetCell == null) {
					targetCell = targetRow.createCell(i);
				}
				
				//拷貝格，包括内容和樣式
				copyCell(targetCell, sourceCell, targetWork, sourceWork, styleMap);
				
				//拷貝格註解
				copyComment(targetCell,sourceCell,targetPatriarch);
			}
		}
	}
    /**
     * 拷貝cell
     * @param targetCell
     * @param sourceCell
     * @param targetWork
     * @param sourceWork
     * @param styleMap
     */
    public static void copyCell(HSSFCell targetCell, HSSFCell sourceCell, HSSFWorkbook targetWork, HSSFWorkbook sourceWork, Map<String,HSSFCellStyle> styleMap) {
		if(targetCell == null || sourceCell == null || targetWork == null || sourceWork == null ){
			throw new IllegalArgumentException("調用PoiUtil.copyCell()方法時，targetCell、sourceCell、targetWork、sourceWork都不能為空，故拋出該異常！");
		}
		
		//處理格子的樣式
		if(styleMap != null){
			if (targetWork == sourceWork) {
				targetCell.setCellStyle(sourceCell.getCellStyle());
			} else {
				String stHashCode = "" + sourceCell.getCellStyle().hashCode();
				HSSFCellStyle targetCellStyle = (HSSFCellStyle) styleMap
						.get(stHashCode);
				if (targetCellStyle == null) {
					targetCellStyle = targetWork.createCellStyle();
					targetCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
					styleMap.put(stHashCode, targetCellStyle);
				}
				
				targetCell.setCellStyle(targetCellStyle);
			}
		}
		
		//處理格子内容
		switch (sourceCell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				targetCell.setCellValue(sourceCell.getRichStringCellValue());
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				targetCell.setCellValue(sourceCell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				targetCell.setCellType(HSSFCell.CELL_TYPE_BLANK);
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				targetCell.setCellValue(sourceCell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				targetCell.setCellFormula(sourceCell.getCellFormula());
				break;
			default:
				break;
		}
	}

    /**
     * 功能：拷貝row
     * @param targetRow
     * @param sourceRow
     * @param styleMap
     * @param targetWork
     * @param sourceWork
     * @param targetPatriarch
     */
    public static void copyRow(HSSFRow targetRow, HSSFRow sourceRow,
                               HSSFWorkbook targetWork, HSSFWorkbook sourceWork, HSSFPatriarch targetPatriarch, HSSFCellStyle style)
            throws Exception {
        if(targetRow == null || sourceRow == null || targetWork == null || sourceWork == null || targetPatriarch == null){
            throw new IllegalArgumentException("調用PoiUtil.copyRow()方法時，targetRow、sourceRow、targetWork、sourceWork、targetPatriarch都不能為空，故拋出該異常！");
        }

        //設置行高
        targetRow.setHeight(sourceRow.getHeight());
        targetRow.setHeightInPoints(sourceRow.getHeightInPoints());

        for (int i = sourceRow.getFirstCellNum(); i <= sourceRow.getLastCellNum(); i++) {
            HSSFCell sourceCell = sourceRow.getCell(i);
            HSSFCell targetCell = targetRow.getCell(i);

            if (sourceCell != null) {
                if (targetCell == null) {
                    targetCell = targetRow.createCell(i);
                }

                //拷貝儲存格，包括內容和樣式
                copyCell(targetCell, sourceCell, targetWork, sourceWork, style);

                //拷貝儲存格注釋
                copyComment(targetCell,sourceCell,targetPatriarch);
            }
        }
    }

    /**
     * 功能：拷貝cell，依據styleMap是否為空判斷是否拷貝儲存格樣式
     * @param targetCell            不能為空
     * @param sourceCell            不能為空
     * @param targetWork            不能為空
     * @param sourceWork            不能為空
     * @param styleMap              可以為空
     */
    public static void copyCell(HSSFCell targetCell, HSSFCell sourceCell, HSSFWorkbook targetWork
            , HSSFWorkbook sourceWork, HSSFCellStyle style) {
        if(targetCell == null || sourceCell == null || targetWork == null || sourceWork == null ){
            throw new IllegalArgumentException("調用PoiUtil.copyCell()方法時，targetCell、sourceCell、targetWork、sourceWork都不能為空，故拋出該異常！");
        }
        HSSFCellStyle targetCellStyle = style;//targetWork.createCellStyle();
        HSSFCellStyle sourceCellStyle = sourceCell.getCellStyle();
        copyCellStyle(sourceWork, sourceCellStyle, targetCellStyle);
        targetCell.setCellStyle(targetCellStyle);

        //處理儲存格內容
        switch (sourceCell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                targetCell.setCellValue(sourceCell.getRichStringCellValue());
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                targetCell.setCellValue(sourceCell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                targetCell.setCellType(HSSFCell.CELL_TYPE_BLANK);
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                targetCell.setCellValue(sourceCell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                targetCell.setCellFormula(sourceCell.getCellFormula());
                break;
            default:
                break;
        }
    }

    /**
     * 功能：拷貝comment
     * @param targetCell
     * @param sourceCell
     * @param targetPatriarch
     */
    public static void copyComment(HSSFCell targetCell, HSSFCell sourceCell, HSSFPatriarch targetPatriarch)throws Exception {
        if(targetCell == null || sourceCell == null || targetPatriarch == null){
            throw new IllegalArgumentException("調用PoiUtil.copyCommentr()方法時，targetCell、sourceCell、targetPatriarch都不能為空，故拋出該異常！");
        }

        //處理儲存格注釋
        HSSFComment comment = sourceCell.getCellComment();
        if(comment != null){
            HSSFComment newComment = targetPatriarch.createComment(new HSSFClientAnchor());
            newComment.setAuthor(comment.getAuthor());
            newComment.setColumn(comment.getColumn());
            newComment.setFillColor(comment.getFillColor());
            newComment.setHorizontalAlignment(comment.getHorizontalAlignment());
            newComment.setLineStyle(comment.getLineStyle());
            newComment.setLineStyleColor(comment.getLineStyleColor());
            newComment.setLineWidth(comment.getLineWidth());
            newComment.setMarginBottom(comment.getMarginBottom());
            newComment.setMarginLeft(comment.getMarginLeft());
            newComment.setMarginTop(comment.getMarginTop());
            newComment.setMarginRight(comment.getMarginRight());
            newComment.setNoFill(comment.isNoFill());
            newComment.setRow(comment.getRow());
            newComment.setShapeType(comment.getShapeType());
            newComment.setString(comment.getString());
            newComment.setVerticalAlignment(comment.getVerticalAlignment());
            newComment.setVisible(comment.isVisible());
            targetCell.setCellComment(newComment);
        }
    }

    /**
     * 功能：複製原有sheet的合併儲存格到新創建的sheet
     *
     * @param sheetCreat
     * @param sourceSheet
     */
    public static void mergerRegion(HSSFSheet targetSheet, HSSFSheet sourceSheet)throws Exception {
        if(targetSheet == null || sourceSheet == null){
            throw new IllegalArgumentException("調用PoiUtil.mergerRegion()方法時，targetSheet或者sourceSheet不能為空，故拋出該異常！");
        }

        for (int i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
            CellRangeAddress oldRange = sourceSheet.getMergedRegion(i);
            CellRangeAddress newRange = new CellRangeAddress(
                    oldRange.getFirstRow(), oldRange.getLastRow(),
                    oldRange.getFirstColumn(), oldRange.getLastColumn());
            targetSheet.addMergedRegion(newRange);
        }
    }


}

