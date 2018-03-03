package com.sap.dataset.service;

import java.io.File;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sap.dataset.application.DatasetUtil;
import com.sap.dataset.factory.DomainObjectFactory;
import com.sap.dataset.factory.file.FileExtractor;
import com.sap.dataset.factory.file.FileTypeFactory;
import com.sap.dataset.model.PublicDataSet;

@Component("fileDataService")
public class FileEndpointService implements EndpointDataService {
	
	@Autowired
	private DomainObjectFactory objectFactory;
	
	@Autowired
	private FileTypeFactory fileTypeFactory;
	
	@Autowired
	private DatasetUtil datasetUtil;

	/*This method retrieves data from endpoint excahnge by calling different data type service
	 * and extractor.
	 * (non-Javadoc)
	 * @see com.sap.dataset.service.EndpointDataService#getData(org.apache.camel.Exchange)
	 */
	@Override
	public PublicDataSet getData(Exchange exchange) {
		
		String domain = (String) exchange.getProperty("domain");
		
		String provider = (String) exchange.getProperty("provider");
		
		if(StringUtils.isEmpty(domain) || StringUtils.isEmpty(provider)){
			throw new IllegalArgumentException("data provider or domain name is not set in the request");
		}
		
		String propertyKey = new StringBuilder(provider).append(".").append(domain)
				   .append(".").append("column").toString();
		String columnNames = datasetUtil.getProperty(propertyKey);
		
		PublicDataSet dataSet = objectFactory.getDomainObject(domain);
		dataSet.setDataProvider(provider);
		dataSet.setDomain(domain);
		
		File incomingFile = exchange.getIn().getBody(File.class);
		
		dataSet.setRawObject(incomingFile);
		dataSet.setProviderColumns(columnNames);
		
		String extension =DatasetUtil.getFileExtension(incomingFile.getName());
		
		FileExtractor extractor = fileTypeFactory.getFileExtractor(extension);
		
		
		try {
			dataSet = extractor.extract(dataSet);
		} catch (Exception e) {
			
		}
		
		return dataSet;
	}
	
	

}
