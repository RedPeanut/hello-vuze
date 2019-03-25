package gudy.azureus2.core3.config;

public class COConfigurationManager {

	/**
	 * raw parameter access
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	public static Object getParameter(String name) throws Exception {
		return ConfigurationManager.getInstance().getParameter(name);
	}
	
	public static boolean setParameter(String parameter, int value) throws Exception {
		return ConfigurationManager.getInstance().setParameter(parameter, value);
	}
	
}
