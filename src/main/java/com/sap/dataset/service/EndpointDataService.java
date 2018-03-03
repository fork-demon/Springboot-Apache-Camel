package com.sap.dataset.service;

import org.apache.camel.Exchange;

import com.sap.dataset.model.PublicDataSet;

public interface EndpointDataService {
	
	public PublicDataSet getData(Exchange dataObject);

}
