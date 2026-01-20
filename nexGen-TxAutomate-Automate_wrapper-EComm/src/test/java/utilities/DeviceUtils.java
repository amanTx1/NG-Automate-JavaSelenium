package utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DeviceUtils {

    // Method to check if adb is available in the system
    public static boolean isAdbAvailable() {
        try {
            String adbVersion = executeCommand("adb version");
            return adbVersion.contains("Android Debug Bridge");
        } catch (Exception e) {
            return false;
        }
    }

    public static String getAndroidDeviceName() {
        String deviceName = executeCommand("adb shell getprop ro.product.model");
        return deviceName.isEmpty() ? "Unknown Device" : deviceName;
    }

    public static String getAndroidPlatformVersion() {
        String platformVersion = executeCommand("adb shell getprop ro.build.version.release");
        return platformVersion.isEmpty() ? "Unknown Version" : platformVersion;
    }

    public static String getAndroidUDID() {
        // Execute adb devices command to list all connected devices
        String devices = executeCommand("adb devices");
        for (String line : devices.split("\n")) {
            if (line.contains("device") && !line.contains("List of devices attached")) {
                return line.split("\\s+")[0]; // Extract the UDID (first column)
            }
        }
        return "Unknown UDID";
    }

    public static String getPlatformName() {
        try {
            // Execute adb devices command
            String devicesOutput = executeCommand("adb devices");

            // Split the output into lines
            String[] lines = devicesOutput.split("\\r?\\n");

            // Check each line for a valid device entry
            for (String line : lines) {
                if (line.matches("^[a-zA-Z0-9\\-_:]+\\s+device$")) {
                    return "Android";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Unknown";
    }

    // Method to list all connected devices
    public static String listAllDevices() {
        if (!isAdbAvailable()) {
            return "adb not found. Ensure it is installed and in PATH.";
        }
        String devices = executeCommand("adb devices");
        return devices.isEmpty() ? "No devices connected." : devices;
    }

    private static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line.trim()).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString().trim();
    }

    // Utility method to execute shell commands
    // private static String executeCommand(String command) {
    //     StringBuilder output = new StringBuilder();
    //     Process process = null;
    //     try {
    //         // Modify command to use full path for adb if adb is not in PATH
    //         if (command.contains("adb")) {
    //             command = "C:\\Users\\Ankita\\AppData\\Local\\Android\\Sdk\\platform-tools\\adb.exe " + command.split("adb")[1];
    //         }
            
    //         process = Runtime.getRuntime().exec(command);
    //         try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
    //             String line;
    //             while ((line = reader.readLine()) != null) {
    //                 output.append(line.trim()).append("\n");
    //             }
    //         }
    //         process.waitFor(); // Ensure the process completes
    //     } catch (Exception e) {
    //         System.err.println("Error executing command: " + command);
    //         e.printStackTrace();
    //     } finally {
    //         if (process != null) {
    //             process.destroy();
    //         }
    //     }
    //     return output.toString().trim();
    // }
    

    // Main method for testing
    public static void main(String[] args) {
        System.out.println("ADB Available: " + DeviceUtils.isAdbAvailable());
        System.out.println("Connected Devices:\n" + DeviceUtils.listAllDevices());
        System.out.println("Device Name: " + DeviceUtils.getAndroidDeviceName());
        System.out.println("Platform Version: " + DeviceUtils.getAndroidPlatformVersion());
        System.out.println("UDID: " + DeviceUtils.getAndroidUDID());
        System.out.println("Platform Name: " + DeviceUtils.getPlatformName());
    }
}
