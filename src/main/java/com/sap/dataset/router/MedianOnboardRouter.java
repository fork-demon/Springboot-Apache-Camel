package com.sap.dataset.router;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sap.dataset.service.ServiceController;

@Component
public class MedianOnboardRouter  extends RouteBuilder{

	
	@Autowired
	@Qualifier("serviceController")
	private ServiceController controller;
	
	@Override
	public void configure() throws Exception {
		
		/* Added route for irs for median -- using apache camel */
		from("{{irs.median.endpint}}").setProperty("domain", simple("median")).setProperty("provider", simple("irs"))
		                .setProperty("type",simple("file")).to("bean:serviceController?method=onboardData(${exchange})");
		
	}

	

}
