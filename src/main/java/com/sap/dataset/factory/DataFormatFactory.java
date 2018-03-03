package com.sap.dataset.factory;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sap.dataset.service.EndpointDataService;

@Component
public class DataFormatFactory {
	
	@Autowired
	@Qualifier("fileDataService")
	private EndpointDataService fileDataService;
	
	
	public EndpointDataService getDataFormat(String dataType){
		
		if("file".equals(dataType)){
			return fileDataService;
		}
		return null;
		
	}

}
