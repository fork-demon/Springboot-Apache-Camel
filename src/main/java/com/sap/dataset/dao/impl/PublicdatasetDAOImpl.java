package com.sap.dataset.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.sap.dataset.cache.PublicDataContext;
import com.sap.dataset.dao.PublicDatasetDAO;

@Component("publicDatasetDAOImpl")
public class PublicdatasetDAOImpl implements PublicDatasetDAO {
	
	//@Autowired
	private JdbcTemplate template = new JdbcTemplate();
	

	@Value("${provider.dataset.query}")
	private String getAllDataProviderQuery;
	
	
	/*
	 * This method saves the values in database, which was retrieved from data provider endpoint
	 * (non-Javadoc)
	 * @see com.sap.dataset.dao.PublicDatasetDAO#save(java.lang.String, java.util.List)
	 */

	@Override
	public void save(String query, List<List<Object>> data) {

		template.batchUpdate(query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement stmt, int i) throws SQLException {
				List<Object> lst = data.get(i);
				int j = 1;
				for (Object object : lst) {
					stmt.setObject(j, object);
					j++;
				}
			}

			@Override
			public int getBatchSize() {
				return data.size();
			}
		});

		
	}
	/* This method fetches the data provider id along with domain name and provider name.
	 * It saves the information in memory cache inside context.
	 * (non-Javadoc)
	 * @see com.sap.dataset.dao.PublicDatasetDAO#loadDataProviderCache()
	 */
	@Override
	public void loadDataProviderCache() {
		
		PublicDataContext context = PublicDataContext.getInstance();
		
		Map<String,Integer> providerMap = new HashMap<>();

		List<Map<String, Object>> dataProviders = template.queryForList(getAllDataProviderQuery);
		
		dataProviders.forEach(map -> {
			String key = new StringBuilder((String)map.get("PROVIDER")).append("||").append((String)map.get("DOMAIN")).toString();
			providerMap.put(key, (Integer)map.get("ID"));
		});
	
		context.buildCache(providerMap);
		
	}

}
