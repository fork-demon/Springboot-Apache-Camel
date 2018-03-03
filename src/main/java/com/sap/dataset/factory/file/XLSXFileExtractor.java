package com.sap.dataset.factory.file;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.sap.dataset.application.DatasetUtil;
import com.sap.dataset.model.ColumnInfo;
import com.sap.dataset.model.PublicDataSet;

@Component("xlsxFileExtractor")
public class XLSXFileExtractor implements FileExtractor {
	
	@Autowired
	private DatasetUtil datasetUtil;
	
	static DataFormatter dataFormatter = new DataFormatter();
	

	@Override
	public PublicDataSet extract(PublicDataSet dataset) throws Exception {


		String[] domainColumns = dataset.getColumns().split(",");
		
		List<List<ColumnInfo>> columnInfoData = new ArrayList<>();
		
		String objectDbmapKey = new StringBuilder(dataset.getDataProvider()).append(".").append(dataset.getDomain()).append(".dbmap").toString();
		
		Map<String,String> objectMap = datasetUtil.getPropertiesMap(objectDbmapKey);
		
		FileInputStream stream = new FileInputStream((File) dataset.getRawObject());
		XSSFWorkbook workbook = new XSSFWorkbook(stream);
		XSSFSheet sheet = workbook.getSheetAt(0);

		int totalRows = sheet.getPhysicalNumberOfRows();

		Map<String, Integer> headerMap = new HashMap<String, Integer>();
		XSSFRow row = sheet.getRow(0); 
		short minColIx = row.getFirstCellNum(); 
		short maxColIx = row.getLastCellNum(); 
		for (short colIx = minColIx; colIx < maxColIx; colIx++) { 
			XSSFCell cell = row.getCell(colIx); // get the cell
			headerMap.put(cell.getStringCellValue(), cell.getColumnIndex());
		}
		for (int x = 1; x <= totalRows; x++) {
			List<ColumnInfo> dataBaseRow = new ArrayList<>();
			XSSFRow dataRow = sheet.getRow(x);
			
			for (String dbColumn : domainColumns) {
				ColumnInfo columnObject = new ColumnInfo();
				columnObject.setColumnName(dbColumn);
				String fileColumn = objectMap.get(dbColumn);
				if (null != fileColumn && null!= dataRow) {
					int index = headerMap.get(fileColumn);
					
					if(-1 != index){
						
						Cell dataCell = dataRow.getCell(index);
						Object val = dataFormatter.formatCellValue(dataCell);
						columnObject.setColumnValue(val);
					}
					
				} else {
					columnObject.setColumnValue(null);
				}
                  dataBaseRow.add(columnObject);
			}
			columnInfoData.add(dataBaseRow);
			
		}
		dataset.setdataSet(columnInfoData);

		return dataset;

	}
}
