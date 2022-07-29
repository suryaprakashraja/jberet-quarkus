package fr.sekaijin.jberet.core;

import java.util.List;
import java.util.Map;
import java.util.Properties;


public class PropertiesBuilder {
	Properties props = new Properties();
	
	final public static Properties EMPTY = new Properties();


	public static PropertiesBuilder properties() {
		return new PropertiesBuilder();
	}

	public PropertiesBuilder() {
	}

	public PropertiesBuilder add(String key, Object value) {
		if (value instanceof List || value instanceof Map) {
			props.setProperty(key, value.toString().replaceAll("^.|.$", ""));
		} else {
			props.setProperty(key, value.toString());
		}
		return this;
	}
	
	public Properties build() {
		return props;
	}
	

}
