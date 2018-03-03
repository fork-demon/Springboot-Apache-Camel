package com.sap.dataset.factory.file;

import java.io.File;

import com.sap.dataset.model.PublicDataSet;

public interface FileExtractor {
	
	public PublicDataSet extract(PublicDataSet dataSet) throws Exception;

}
