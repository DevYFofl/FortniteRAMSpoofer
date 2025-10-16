package com.devyf.ramspoof;

import android.app.ActivityManager;
import android.os.Build;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.io.RandomAccessFile;
import java.lang.reflect.Field;

public class RAMSpoofModule implements IXposedHookLoadPackage {

    // التطبيقات المستهدفة
    private static final String TARGET_PKG = "com.epicgames.fortnite";
    private static final String TARGET_PKG_ALT = "kbi.ntjgzgmei.mkqmbnhc";

    // قيم الذاكرة المزيفة - 8GB
    private static final long NEW_TOTAL_RAM_BYTES = 8L * 1024L * 1024L * 1024L;
    private static final long NEW_AVAIL_RAM_BYTES = 6L * 1024L * 1024L * 1024L;

    // معلومات الجهاز المزيفة
    private static final String FAKE_DEVICE = "SM-G973F";
    private static final String FAKE_MODEL = "Samsung Galaxy S10";
    private static final String FAKE_MANUFACTURER = "Samsung";
    private static final String FAKE_HARDWARE = "exynos9820";
    private static final String FAKE_GPU = "Mali-G76 MP12";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        // التحقق من الحزمة المستهدفة
        if (!lpparam.packageName.equals(TARGET_PKG) && 
            !lpparam.packageName.equals(TARGET_PKG_ALT)) {
            return;
        }

        XposedBridge.log("RAMSpoof: Hooking " + lpparam.packageName);

        // Hook 1: ActivityManager.getMemoryInfo
        hookMemoryInfo(lpparam);
        
        // Hook 2: Runtime memory methods
        hookRuntimeMemory(lpparam);
        
        // Hook 3: /proc/meminfo reading
        hookProcMeminfo(lpparam);
        
        // Hook 4: Build properties
        hookBuildProperties(lpparam);
        
        // Hook 5: SystemProperties
        hookSystemProperties(lpparam);
        
        // Hook 6: GPU info
        hookGPUInfo(lpparam);
    }

    private void hookMemoryInfo(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.findAndHookMethod(
                ActivityManager.class,
                "getMemoryInfo",
                ActivityManager.MemoryInfo.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        ActivityManager.MemoryInfo memInfo = (ActivityManager.MemoryInfo) param.args[0];
                        memInfo.totalMem = NEW_TOTAL_RAM_BYTES;
                        memInfo.availMem = NEW_AVAIL_RAM_BYTES;
                        memInfo.threshold = NEW_TOTAL_RAM_BYTES / 10;
                        memInfo.lowMemory = false;
                        XposedBridge.log("RAMSpoof: Spoofed MemoryInfo to 8GB");
                    }
                }
            );
        } catch (Throwable t) {
            XposedBridge.log("RAMSpoof: Failed to hook MemoryInfo - " + t.getMessage());
        }
    }

    private void hookRuntimeMemory(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.findAndHookMethod(
                Runtime.class,
                "totalMemory",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(NEW_TOTAL_RAM_BYTES);
                    }
                }
            );

            XposedHelpers.findAndHookMethod(
                Runtime.class,
                "maxMemory",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(NEW_TOTAL_RAM_BYTES);
                    }
                }
            );

            XposedHelpers.findAndHookMethod(
                Runtime.class,
                "freeMemory",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(NEW_AVAIL_RAM_BYTES);
                    }
                }
            );
            
            XposedBridge.log("RAMSpoof: Spoofed Runtime memory methods");
        } catch (Throwable t) {
            XposedBridge.log("RAMSpoof: Failed to hook Runtime - " + t.getMessage());
        }
    }

    private void hookProcMeminfo(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.findAndHookMethod(
                RandomAccessFile.class,
                "readLine",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String line = (String) param.getResult();
                        if (line != null) {
                            if (line.startsWith("MemTotal:")) {
                                long totalKB = NEW_TOTAL_RAM_BYTES / 1024;
                                param.setResult("MemTotal:       " + totalKB + " kB");
                            } else if (line.startsWith("MemFree:") || line.startsWith("MemAvailable:")) {
                                long availKB = NEW_AVAIL_RAM_BYTES / 1024;
                                param.setResult(line.substring(0, line.indexOf(':') + 1) + "       " + availKB + " kB");
                            }
                        }
                    }
                }
            );
        } catch (Throwable t) {
            XposedBridge.log("RAMSpoof: Failed to hook /proc/meminfo - " + t.getMessage());
        }
    }

    private void hookBuildProperties(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.setStaticObjectField(Build.class, "DEVICE", FAKE_DEVICE);
            XposedHelpers.setStaticObjectField(Build.class, "MODEL", FAKE_MODEL);
            XposedHelpers.setStaticObjectField(Build.class, "MANUFACTURER", FAKE_MANUFACTURER);
            XposedHelpers.setStaticObjectField(Build.class, "HARDWARE", FAKE_HARDWARE);
            XposedHelpers.setStaticObjectField(Build.class, "BOARD", FAKE_HARDWARE);
            
            XposedBridge.log("RAMSpoof: Spoofed Build properties");
        } catch (Throwable t) {
            XposedBridge.log("RAMSpoof: Failed to hook Build - " + t.getMessage());
        }
    }

    private void hookSystemProperties(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            Class<?> systemProperties = XposedHelpers.findClass("android.os.SystemProperties", lpparam.classLoader);

            XposedHelpers.findAndHookMethod(
                systemProperties,
                "get",
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String key = (String) param.args[0];
                        spoofSystemProperty(key, param);
                    }
                }
            );

            XposedHelpers.findAndHookMethod(
                systemProperties,
                "get",
                String.class,
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String key = (String) param.args[0];
                        spoofSystemProperty(key, param);
                    }
                }
            );

            XposedBridge.log("RAMSpoof: Spoofed SystemProperties");
        } catch (Throwable t) {
            XposedBridge.log("RAMSpoof: Failed to hook SystemProperties - " + t.getMessage());
        }
    }

    private void spoofSystemProperty(String key, XC_MethodHook.MethodHookParam param) {
        switch (key) {
            case "ro.product.model":
                param.setResult(FAKE_MODEL);
                break;
            case "ro.product.manufacturer":
                param.setResult(FAKE_MANUFACTURER);
                break;
            case "ro.product.device":
            case "ro.build.product":
                param.setResult(FAKE_DEVICE);
                break;
            case "ro.hardware":
            case "ro.board.platform":
                param.setResult(FAKE_HARDWARE);
                break;
            case "dalvik.vm.heapsize":
                param.setResult("512m");
                break;
        }
    }

    private void hookGPUInfo(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            Class<?> gles20 = XposedHelpers.findClass("android.opengl.GLES20", lpparam.classLoader);
            XposedHelpers.findAndHookMethod(
                gles20,
                "glGetString",
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        int name = (int) param.args[0];
                        if (name == 0x1F01) { // GL_RENDERER
                            param.setResult(FAKE_GPU);
                            XposedBridge.log("RAMSpoof: Spoofed GPU to " + FAKE_GPU);
                        }
                    }
                }
            );
        } catch (Throwable t) {
            XposedBridge.log("RAMSpoof: Failed to hook GPU - " + t.getMessage());
        }
    }
}