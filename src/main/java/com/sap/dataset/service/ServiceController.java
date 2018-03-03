package com.sap.dataset.service;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sap.dataset.dao.PublicDatasetDAO;
import com.sap.dataset.factory.DataFormatFactory;
import com.sap.dataset.model.PublicDataSet;

@Component("serviceController")
public class ServiceController {
	
	@Autowired
	private DataFormatFactory dataTypeFactory;
	
	@Autowired
	private DeliveryService deliveryService;
	
	/*
	 * This method retrieves the end point data from camel exchange 
	 * and deliver that data to destination. 
	 * @param exchange
	 */
	 public void onboardData(Exchange exchange){
		 
		 String dataFormatType = (String) exchange.getProperty("type");
		 if(StringUtils.isEmpty(dataFormatType)){
			 throw new IllegalArgumentException("data format type is null");
		 }
		 EndpointDataService service = dataTypeFactory.getDataFormat(dataFormatType);
		 
		 PublicDataSet data = service.getData(exchange);
		 
		 deliveryService.deliver(data);
		 
			
	 }

}
