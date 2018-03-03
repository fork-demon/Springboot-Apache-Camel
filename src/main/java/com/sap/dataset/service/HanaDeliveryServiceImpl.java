package com.sap.dataset.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.sap.dataset.application.DatasetQueryBuilder;
import com.sap.dataset.application.DatasetUtil;
import com.sap.dataset.cache.PublicDataContext;
import com.sap.dataset.dao.PublicDatasetDAO;
import com.sap.dataset.model.ColumnInfo;
import com.sap.dataset.model.PublicDataSet;

@Component
public class HanaDeliveryServiceImpl implements DeliveryService{
	
	@Autowired
	private PublicDatasetDAO publicDatasetDAOImpl;
	
	@Autowired
	private DatasetUtil datasetUtil;

	/*This method delivers data to Hana database through spring jdbc template, it adds
	 * few extra column and foreign key reference to the data
	 * (non-Javadoc)
	 * @see com.sap.dataset.service.DeliveryService#deliver(com.sap.dataset.model.PublicDataSet)
	 */
	@Override
	public void deliver(PublicDataSet dataset) {

		// add extra field map and then add else if condition below

		List<List<ColumnInfo>> data = dataset.getDataSet();

		String providerKey = dataset.getDataProvider() + "||" + dataset.getDomain();

		// get privider ID from cache for data provider
		int providerId = PublicDataContext.getInstance().getCache().get(providerKey);

		String extraFieldKey =  new StringBuilder(dataset.getDataProvider()).append(".").append(dataset.getDomain())
								.append(".").append("extracolumn").toString();
		
	
		Map<String, String> extraFieldMap = datasetUtil.getPropertiesMap(extraFieldKey);
		
		List<String> columnNames = new LinkedList<>();
		List<ColumnInfo> firstRow = data.get(0);

		firstRow.forEach(column -> {
			columnNames.add(column.getColumnName());
		});

		List<Object> rowData;
		List<List<Object>> tableData = new ArrayList<>();

		for (List<ColumnInfo> lst : data) {

			rowData = new ArrayList<>();

			for (ColumnInfo column : lst) {
				// add data provider id to all data
				if ("DATA_PROVIDER_ID".equals(column.getColumnName())) {
					rowData.add(providerId);
				}
				else if (extraFieldMap.containsKey(column.getColumnName())) {
					// adding extra column's value such as Currency,Country etc
					rowData.add(extraFieldMap.get(column.getColumnName()));
				} else {
					rowData.add(column.getColumnValue());
				}

			}
			tableData.add(rowData);
		}

		// Create the database query based on the table name and columns 
		String query = new DatasetQueryBuilder().addTable(dataset.getTableName()).addLeftBrace()
				.addColumnNames(columnNames).addRightBrace().addvalue().addLeftBrace()
				.addValuePlceHolder(columnNames.size()).addRightBrace().build();

		// call database to store value in data.
		publicDatasetDAOImpl.save(query, tableData);

	}

}
