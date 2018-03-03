package com.sap.dataset.dao;

import java.util.List;

public interface PublicDatasetDAO {
	
	public void loadDataProviderCache();
	
	public void save(String query, List<List<Object>> data);

}
