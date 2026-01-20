package utilities;

public class EnvConfig {
   
    public static String getUsername() {
        return ConfigReader.getValue("USER_NAME");
    }
    
    public static String getPassword() {
        return ConfigReader.getValue("PASSWORD");
    }
    
    public static String getBaseUrl() {
        return ConfigReader.getValue("BASE_URL");
    }
}

