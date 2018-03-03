package com.sap.dataset.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("medianDataSet")
public class MedianDataset implements PublicDataSet {

	private String dataProvider;

	private String domain = "Median";
	
	private Object rawObject;
	
	private String providerColumn;

	List<List<ColumnInfo>> medianIncomeList;

	@Value("${median.column.names}")
	private String columnNames;
	
	@Value("${median.table.name}")
	private String tableName;

	@Override
	public List<List<ColumnInfo>> getDataSet() {

		return medianIncomeList;

	}

	@Override
	public void setdataSet(List<List<ColumnInfo>> dataSet) {

		medianIncomeList = (List<List<ColumnInfo>>)dataSet;
	}

	@Override
	public String getColumns() {
		return columnNames;
	}

	public String getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(String dataProvider) {
		this.dataProvider = dataProvider;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public void setRawObject(Object obj) {
		rawObject = obj;
		
	}

	@Override
	public Object getRawObject() {
		
		return rawObject;
	}

	@Override
	public void setProviderColumns(String column) {
		providerColumn = column;
		
	}

	@Override
	public String getProviderColumns() {
		return providerColumn;
	}

	@Override
	public String getTableName() {
		return tableName;
	}
	
	

}
