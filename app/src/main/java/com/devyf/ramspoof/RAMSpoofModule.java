
package com.devyf.ramspoof;

import android.app.ActivityManager;
import android.os.Build;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;

public class RAMSpoofModule implements IXposedHookLoadPackage {

    // \u0642\u064a\u0645 \u0627\u0644\u0630\u0627\u0643\u0631\u0629 \u0627\u0644\u0645\u0632\u064a\u0641\u0629 - محسّنة للأجهزة 2GB (تزييف 4GB)
    private static final long NEW_TOTAL_RAM_BYTES = 4L * 1024L * 1024L * 1024L;
    private static final long NEW_AVAIL_RAM_BYTES = 3L * 1024L * 1024L * 1024L;
    
    // \u0645\u0639\u0644\u0648\u0645\u0627\u062a \u062c\u0647\u0627\u0632 Samsung Galaxy S10 \u062d\u0642\u064a\u0642\u064a\u0629
    private static final String FAKE_DEVICE = "beyond1";
    private static final String FAKE_MODEL = "SM-G973F";
    private static final String FAKE_PRODUCT = "beyond1ltexx";
    private static final String FAKE_MANUFACTURER = "Samsung";
    private static final String FAKE_HARDWARE = "exynos9820";
    private static final String FAKE_GPU = "Mali-G76 MP12";
    private static final String FAKE_BRAND = "samsung";
    private static final String FAKE_BOARD = "exynos9820";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        // \u0627\u0644\u062a\u062d\u0642\u0642 \u0645\u0646 \u0627\u0644\u062d\u0632\u0645\u0629 \u0627\u0644\u0645\u0633\u062a\u0647\u062f\u0641\u0629 \u0641\u0642\u0637 - Fortnite
        if (!lpparam.packageName.equals("com.epicgames.fortnite") && 
            !lpparam.packageName.equals("kbi.ntjgzgmei.mkqmbnhc") &&
            !lpparam.packageName.equals("com.epicgames.portal")) {
            return; // \u0644\u0627 \u062a\u0639\u0645\u0644 \u0639\u0644\u0649 \u0623\u064a \u062a\u0637\u0628\u064a\u0642 \u0622\u062e\u0631
        }

        XposedBridge.log("RAMSpoof: Activated for Fortnite - " + lpparam.packageName);

        // Hook 1: ActivityManager.getMemoryInfo (\u0627\u0644\u0623\u0647\u0645)
        hookMemoryInfo(lpparam);
        
        // Hook 2: Build properties (\u0645\u062e\u0641\u064a)
        hookBuildProperties(lpparam);
        
        // Hook 3: SystemProperties (\u0645\u062e\u0641\u064a)
        hookSystemProperties(lpparam);
        
        // Hook 4: /proc/meminfo (\u0645\u062e\u0641\u064a \u062c\u062f\u0627\u064b)
        hookProcMeminfo(lpparam);
        
        // Hook 5: Runtime memory (\u0625\u0636\u0627\u0641\u064a)
        hookRuntimeMemory(lpparam);
        
        // Hook 6: GPU info (\u0645\u0647\u0645 \u062c\u062f\u0627\u064b)
        hookGPUInfo(lpparam);
        
        // Hook 7: ActivityManager.getMemoryClass (\u062c\u062f\u064a\u062f)
        hookMemoryClass(lpparam);
        
        // Hook 8: Context.getSystemService (\u062c\u062f\u064a\u062f)
        hookSystemService(lpparam);
    }

    private void hookMemoryInfo(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.findAndHookMethod(
                "android.app.ActivityManager",
                lpparam.classLoader,
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
                        // \u0644\u0627 \u062a\u0633\u062c\u0644 \u0623\u064a \u0634\u064a\u0621 \u0644\u062a\u062c\u0646\u0628 \u0627\u0644\u0643\u0634\u0641
                    }
                }
            );
        } catch (Throwable t) {
            // \u0644\u0627 \u062a\u0633\u062c\u0644 \u0627\u0644\u0623\u062e\u0637\u0627\u0621 \u0644\u062a\u062c\u0646\u0628 \u0627\u0644\u0643\u0634\u0641
        }
    }

    private void hookBuildProperties(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            // \u062a\u0639\u062f\u064a\u0644 \u062e\u0635\u0627\u0626\u0635 Build \u0628\u0634\u0643\u0644 \u0645\u062e\u0641\u064a
            XposedHelpers.setStaticObjectField(Build.class, "DEVICE", FAKE_DEVICE);
            XposedHelpers.setStaticObjectField(Build.class, "MODEL", FAKE_MODEL);
            XposedHelpers.setStaticObjectField(Build.class, "PRODUCT", FAKE_PRODUCT);
            XposedHelpers.setStaticObjectField(Build.class, "MANUFACTURER", FAKE_MANUFACTURER);
            XposedHelpers.setStaticObjectField(Build.class, "HARDWARE", FAKE_HARDWARE);
            XposedHelpers.setStaticObjectField(Build.class, "BOARD", FAKE_BOARD);
            XposedHelpers.setStaticObjectField(Build.class, "BRAND", FAKE_BRAND);
            
            // Build.VERSION
            XposedHelpers.setStaticObjectField(Build.VERSION.class, "RELEASE", "11");
            XposedHelpers.setStaticObjectField(Build.VERSION.class, "SDK_INT", 30);
            
        } catch (Throwable t) {
            // \u062a\u062c\u0627\u0647\u0644 \u0627\u0644\u0623\u062e\u0637\u0627\u0621
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
                        String result = spoofSystemProperty(key);
                        if (result != null) {
                            param.setResult(result);
                        }
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
                        String result = spoofSystemProperty(key);
                        if (result != null) {
                            param.setResult(result);
                        }
                    }
                }
            );

            XposedHelpers.findAndHookMethod(
                systemProperties,
                "getInt",
                String.class,
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String key = (String) param.args[0];
                        Integer result = spoofSystemPropertyInt(key);
                        if (result != null) {
                            param.setResult(result);
                        }
                    }
                }
            );
            
        } catch (Throwable t) {
            // \u062a\u062c\u0627\u0647\u0644 \u0627\u0644\u0623\u062e\u0637\u0627\u0621
        }
    }

    private String spoofSystemProperty(String key) {
        switch (key) {
            case "ro.product.model":
            case "ro.product.vendor.model":
                return FAKE_MODEL;
            case "ro.product.manufacturer":
            case "ro.product.vendor.manufacturer":
                return FAKE_MANUFACTURER;
            case "ro.product.device":
            case "ro.product.vendor.device":
            case "ro.build.product":
                return FAKE_DEVICE;
            case "ro.product.name":
            case "ro.product.vendor.name":
                return FAKE_PRODUCT;
            case "ro.hardware":
            case "ro.board.platform":
            case "ro.hardware.platform":
                return FAKE_HARDWARE;
            case "ro.product.brand":
            case "ro.product.vendor.brand":
                return FAKE_BRAND;
            case "ro.board":
                return FAKE_BOARD;
            case "dalvik.vm.heapsize":
                return "384m";
            case "dalvik.vm.heapstartsize":
                return "8m";
            case "dalvik.vm.heapgrowthlimit":
                return "192m";  // محسّن للأجهزة 2GB
            case "dalvik.vm.heaptargetutilization":
                return "0.75";
            case "ro.config.low_ram":
                return "false";
            case "ro.lmk.low":
                return "false";
            case "sys.mem.total":
                return String.valueOf(NEW_TOTAL_RAM_BYTES);
        }
        return null;
    }

    private Integer spoofSystemPropertyInt(String key) {
        switch (key) {
            case "ro.config.low_ram":
                return 0;
            case "ro.lmk.low":
                return 0;
        }
        return null;
    }

    private void hookProcMeminfo(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            // Hook FileInputStream \u0644\u0642\u0631\u0627\u0621\u0629 /proc/meminfo
            XposedHelpers.findAndHookMethod(
                "java.io.FileInputStream",
                lpparam.classLoader,
                "read",
                byte[].class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        try {
                            byte[] buffer = (byte[]) param.args[0];
                            String content = new String(buffer);
                            
                            if (content.contains("MemTotal:")) {
                                long totalKB = NEW_TOTAL_RAM_BYTES / 1024;
                                String fakeContent = content.replaceAll(
                                    "MemTotal:\\s+\\d+\\s+kB",
                                    "MemTotal:       " + totalKB + " kB"
                                );
                                
                                fakeContent = fakeContent.replaceAll(
                                    "MemAvailable:\\s+\\d+\\s+kB",
                                    "MemAvailable:    " + (NEW_AVAIL_RAM_BYTES / 1024) + " kB"
                                );
                                
                                fakeContent = fakeContent.replaceAll(
                                    "MemFree:\\s+\\d+\\s+kB",
                                    "MemFree:         " + (NEW_AVAIL_RAM_BYTES / 1024) + " kB"
                                );
                                
                                System.arraycopy(fakeContent.getBytes(), 0, buffer, 0, 
                                    Math.min(buffer.length, fakeContent.length()));
                            }
                        } catch (Exception e) {
                            // \u062a\u062c\u0627\u0647\u0644 \u0627\u0644\u0623\u062e\u0637\u0627\u0621
                        }
                    }
                }
            );
        } catch (Throwable t) {
            // \u062a\u062c\u0627\u0647\u0644 \u0627\u0644\u0623\u062e\u0637\u0627\u0621
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
        } catch (Throwable t) {
            // \u062a\u062c\u0627\u0647\u0644 \u0627\u0644\u0623\u062e\u0637\u0627\u0621
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
                        } else if (name == 0x1F00) { // GL_VENDOR
                            param.setResult("ARM");
                        } else if (name == 0x1F02) { // GL_VERSION
                            param.setResult("OpenGL ES 3.2 v1.r19p0-01rel0.89f2956f2c");
                        }
                    }
                }
            );
        } catch (Throwable t) {
            // \u062a\u062c\u0627\u0647\u0644 \u0627\u0644\u0623\u062e\u0637\u0627\u0621
        }
    }

    private void hookMemoryClass(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.findAndHookMethod(
                "android.app.ActivityManager",
                lpparam.classLoader,
                "getMemoryClass",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(384); // محسّن لـ 2GB - 384MB heap size
                    }
                }
            );

            XposedHelpers.findAndHookMethod(
                "android.app.ActivityManager",
                lpparam.classLoader,
                "getLargeMemoryClass",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(512); // محسّن لـ 2GB - 512MB large heap
                    }
                }
            );
        } catch (Throwable t) {
            // \u062a\u062c\u0627\u0647\u0644 \u0627\u0644\u0623\u062e\u0637\u0627\u0621
        }
    }

    private void hookSystemService(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.findAndHookMethod(
                "android.app.ContextImpl",
                lpparam.classLoader,
                "getSystemService",
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String serviceName = (String) param.args[0];
                        if ("activity".equals(serviceName)) {
                            // \u064a\u0645\u0643\u0646 \u0625\u0636\u0627\u0641\u0629 hooks \u0625\u0636\u0627\u0641\u064a\u0629 \u0647\u0646\u0627 \u0625\u0630\u0627 \u0644\u0632\u0645 \u0627\u0644\u0623\u0645\u0631
                        }
                    }
                }
            );
        } catch (Throwable t) {
            // \u062a\u062c\u0627\u0647\u0644 \u0627\u0644\u0623\u062e\u0637\u0627\u0621
        }
    }
}
