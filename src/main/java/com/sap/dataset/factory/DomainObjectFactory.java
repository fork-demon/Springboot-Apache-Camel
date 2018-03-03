package com.sap.dataset.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sap.dataset.model.PublicDataSet;

@Component
public class DomainObjectFactory {
	
	@Autowired
	@Qualifier("medianDataSet")
	private PublicDataSet medianDataSet;
	
	public PublicDataSet getDomainObject(String domainName){
		
		if("median".equals(domainName)){
			return medianDataSet;
		}
		return null;
	}

}
