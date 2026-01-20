
package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
/**
 * This ConfigReader file will read the config file 
 *
 */

public class ConfigReader {
	

	/**
	 * will read the properties file with this function
	 * @param filePath
	 * @return
	 */
	private ConfigReader(){
		
	}
	public static Properties loadPropertyFile(String filePath) {
		// Read from properties file
		File file = new File(filePath);
		Properties prop = new Properties();

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
			prop.load(fileInput);
		} catch (Exception e) {
			LogUtil.errorLog(ConfigReader.class, "Caught the exception", e);
		}
		return prop;

	}
	/**
	 * will get sting value from properties file
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {

		Properties prop = loadPropertyFile("src/main/resources/Config/config.properties");
		// Open the URL in firefox browser
		 return prop.getProperty(key);
	}
	/**
	 * will get int value from properties file
	 * @param key
	 * @return
	 */
	public static int getIntValue(String key) {
		Properties prop = loadPropertyFile("src/main/resources/Config/config.properties");

		// Open the URL in firefox browser
		String strKey = prop.getProperty(key);

		return Integer.parseInt(strKey);
	}

	private static Properties properties;
    
    static {
        try {
            properties = new Properties();
            FileInputStream file = new FileInputStream("src/main/resources/Config/config.properties");
            properties.load(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties");
        }
    }

	public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getBaseUri() {
        return getProperty("api.baseUri");
    }
    
    public static String getBasePath() {
        return getProperty("api.basePath");
    }
    
    public static String getApiToken() {
        return getProperty("api.token");
    }

}
