package com.sap.dataset.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DatasetUtil {
	
	@Autowired
	private Environment environment;
	
	public static String getFileExtension(String fileName) {
		
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	public Map<String,String> getPropertiesMap(String key) {
		
		String fileObjectMapString = environment.getProperty(key);
		
		Map<String,String> objectMap = new HashMap<>();
		
		
		for(String keyValue: fileObjectMapString.split(",")) {
			String[] pair = keyValue.split(":");
			objectMap.put(pair[0], pair[1]);
		}
		return objectMap;
		
	}
	
	public String getProperty(String key) {
		
		if(StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException("Couldnt find property value, key is null");
		}
		
		return environment.getProperty(key);
	}

}
