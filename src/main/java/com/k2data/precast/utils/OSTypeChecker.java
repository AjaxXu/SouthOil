package com.k2data.precast.utils;

/**
 * @author Louis
 */
public class OSTypeChecker {
    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isLinux() {
        return OS.contains("linux");
    }

    public static boolean isMacOS() {
        return OS.contains("mac") && OS.indexOf("os") > 0 && !OS.contains("x");
    }

    public static boolean isMacOSX() {
        return OS.contains("mac") && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
    }

    public static boolean isWindows() {
        return OS.contains("windows");
    }
}
