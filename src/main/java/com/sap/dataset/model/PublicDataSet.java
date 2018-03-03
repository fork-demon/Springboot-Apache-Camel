package com.sap.dataset.model;

import java.util.List;

public interface PublicDataSet {

	public List<List<ColumnInfo>> getDataSet();
	
	public String getColumns();
	
	public void setdataSet(List<List<ColumnInfo>> dataSet);
	
	public void setRawObject(Object obj);
	
	public Object getRawObject();
	
	public void setProviderColumns(String columnNames);
	
	public String getProviderColumns();

	public String getDataProvider();
	
	public String getDomain();
	
	public String getTableName();
	
	public void setDataProvider(String dataProvider);
	
	public void setDomain(String dataProvider);
	
}
