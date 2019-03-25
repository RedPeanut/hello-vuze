package gudy.azureus2.core3.config;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import gudy.azureus2.core3.util.Constants;
import gudy.azureus2.core3.util.FileUtil;

public class ConfigurationManager {

	private ConcurrentHashMap<String, Object> propertiesMap;
	
	private static ConfigurationManager config;
	
	public static ConfigurationManager getInstance() throws Exception {
		if (config == null) {
			config = new ConfigurationManager();
			config.load();
		}
		return config;
	}
	
	public void load() throws Exception {
		load("hello.azureus.config");
	}
	
	public void load(String filename) throws Exception {
		Map data = FileUtil.readResilientConfigFile(filename);
		if (propertiesMap == null) {
			ConcurrentHashMap<String, Object> c_map = new ConcurrentHashMap<>(data.size()+256, 0.75f, 8);
			c_map.putAll(data);
			propertiesMap = c_map;
		}
	}
	
	public void save() throws Exception {
		save("hello.azureus.config");
	}
	
	public void save(String filename) throws Exception {
		TreeMap<String,Object> propertiesClone = null; //propertiesMap.toTreeMap();
		FileUtil.writeResilientConfigFile(filename, propertiesClone);
	}
	
	public int getIntParameter(String parameter) {
		return getIntParameter(parameter, -1);
	}
	
	public int getIntParameter(String parameter, int defaultValue) {
		Long v = getLongParameterRaw(parameter);
		return v != null ? v.intValue() : defaultValue;
	}
	
	private Long getLongParameterRaw(String parameter) {
		return (Long) propertiesMap.get(parameter);
	}

	public Object getParameter(String name) {
		Object value = propertiesMap.get(name);
		return value;
	}

	/*public boolean setParameter(String parameter, Object value) {
		Object oldValue = propertiesMap.put(parameter, value);
		return true;
	}*/
	
	public boolean setParameter(String parameter, byte[] value) {
		byte[] oldValue = (byte[]) propertiesMap.put(parameter, value);
		return true;
	}
	
	public boolean setParameter(String parameter, float value) {
		String newValue = String.valueOf(value);
		return setParameter(parameter, stringToBytes(newValue));
	}
	
	public boolean setParameter(String parameter, long value) {
		Long newValue = new Long(value);
		Long oldValue = (Long) propertiesMap.put(parameter, newValue);
		return false;
	}
	
	public boolean setParameter(String parameter, int value) {
		Long newValue = new Long(value);
		Long oldValue = (Long) propertiesMap.put(parameter, newValue);
		return false;
	}

	private byte[] stringToBytes(String str) {
		if (str == null)
			return (null);
		
		try {
			return (str.getBytes(Constants.DEFAULT_ENCODING));
		} catch (Throwable e) {
			return (str.getBytes());
		}
	}
}
