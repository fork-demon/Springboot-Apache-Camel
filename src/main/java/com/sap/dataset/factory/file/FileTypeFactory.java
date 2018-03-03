package com.sap.dataset.factory.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component("fileTypefactory")
public class FileTypeFactory {
	
	
	@Autowired
	@Qualifier("xlsxFileExtractor")
	private FileExtractor xlsxFileExtractor;
	
	public FileExtractor getFileExtractor(String extension){
		
		if("xlsx".equals(extension)){
			return xlsxFileExtractor;
		}
		return null;
		
	}

}
