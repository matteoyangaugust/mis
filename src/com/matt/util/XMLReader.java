package com.matt.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class XMLReader {
	private XMLConfiguration config;
	private static Logger log = Logger.getLogger(XMLReader.class);
	private XMLReader(String fileName) {

		//默認的分隔符號為","，因此必須改為SQL不會用到的符號
		XMLConfiguration.setDefaultListDelimiter('€');
		try {
			config = new XMLConfiguration(fileName);
			config.setValidating(false);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static XMLReader getInstance(Class<?> clazz) {
		String clazzName = clazz.getName();
		String xmlPath = clazzName.replaceAll("\\.", "/") + ".xml";
		return getInstance(xmlPath);
	}

	public static XMLReader getInstance(String fileName) {
		XMLReader mySelf = new XMLReader(fileName);

		return mySelf;
	}

	/**
	 * 取得 <tt>tag<tt> 所在的SQL字串
	 * @param tag
	 * @return
	 */
	public String getString(String tag) {
		if(StringUtils.trimToNull(tag) == null){
			log.info("SQL script 是空的...");
		}
		return config.getString(tag);
	}
}
