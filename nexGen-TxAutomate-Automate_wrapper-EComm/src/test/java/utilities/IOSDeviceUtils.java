package utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class IOSDeviceUtils {
    public static String getIOSDeviceName() {
        return executeCommand("ideviceinfo -k DeviceName");
    }

    public static String getIOSPlatformVersion() {
        return executeCommand("ideviceinfo -k ProductVersion");
    }

    public static String getIOSUDID() {
        return executeCommand("ideviceinfo -k UniqueDeviceID");
    }

    public static String getIOSPlatformName() {
        // Validate if a connected device responds, then return "iOS"
        String deviceInfo = executeCommand("ideviceinfo -k ProductType");
        if (deviceInfo != null && !deviceInfo.isEmpty()) {
            return "iOS";
        } else {
            return "Unknown";
        }
    }

    private static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}

