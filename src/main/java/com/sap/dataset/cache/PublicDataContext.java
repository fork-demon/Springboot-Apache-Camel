package com.sap.dataset.cache;

import java.util.Map;

public class PublicDataContext {

	private Map<String,Integer> dataProvidersMap;

	private PublicDataContext() {

	}

	public static PublicDataContext getInstance() {
		return CacheHolder.INSTANCE;
	}

	public Map<String,Integer> getCache() {
		return dataProvidersMap;
	}

	public void buildCache( Map<String, Integer> dataProviders) {

	  dataProvidersMap = dataProviders;
	}

	static class CacheHolder {

		public static final PublicDataContext INSTANCE = new PublicDataContext();
	}

}
