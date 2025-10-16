# 🎮 Fortnite RAM Spoofer

Simple Xposed + Magisk modules to spoof device info and run Fortnite on 2GB RAM devices.

## ⚠️ Important Note

This is a **SIMPLE SPOOFER ONLY** - no performance tweaks, no Zram, no Swap.
Just device info spoofing to bypass Fortnite's RAM check.

---

## 📦 What's Included

### 1. Xposed Module
- Spoofs RAM to 8GB
- Spoofs device model to Samsung Galaxy S10
- Spoofs hardware info
- Spoofs GPU info

### 2. Magisk Module
- Changes system properties
- Spoofs device model
- Spoofs hardware info
- **NO performance tweaks**
- **NO Zram/Swap**

---

## 🔧 Requirements

- ✅ Android 7.0+
- ✅ Root (Magisk)
- ✅ LSPosed or EdXposed
- ✅ 2GB RAM device

---

## 📥 Installation

### Step 1: Install Xposed Module

1. Build the APK:
   ```bash
   cd FortniteRAMSpoofer
   ./gradlew assembleRelease
   ```
   Or use Android Studio to build

2. Install the APK on your device

3. Open LSPosed Manager

4. Enable "Fortnite RAM Spoofer" module

5. Select target apps:
   - `com.epicgames.fortnite`
   - `kbi.ntjgzgmei.mkqmbnhc` (if using modded version)

6. Reboot device

### Step 2: Install Magisk Module

1. Create the module zip:
   ```bash
   cd magisk
   zip -r FortniteSpoofer.zip *
   ```

2. Open Magisk Manager

3. Go to Modules → Install from storage

4. Select `FortniteSpoofer.zip`

5. Reboot device

---

## 🎮 Usage

1. Make sure both modules are installed and enabled

2. Reboot your device

3. Launch Fortnite

4. Lower graphics settings:
   - Quality: Low
   - 3D Resolution: 50%
   - FPS: 30

5. Enjoy!

---

## 📊 Expected Results

| Feature | Result |
|---------|--------|
| Game Launch | ✅ Yes |
| FPS | 20-30 |
| Graphics | Low |
| Stability | Medium |

---

## 🔍 Troubleshooting

### "3GB RAM Required" message

1. Check if Xposed module is enabled in LSPosed
2. Make sure target app is selected
3. Clear Fortnite data: `pm clear com.epicgames.fortnite`
4. Reboot device

### Game crashes

1. Lower graphics settings more
2. Close all background apps
3. Reboot device

---

## ⚠️ Disclaimer

- This is experimental and for personal use only
- Use at your own risk
- May void device warranty
- Performance will be lower than 4GB+ devices

---

## 📝 What This Does

### Xposed Module Hooks:
1. `ActivityManager.getMemoryInfo` - Spoofs total/available RAM
2. `Runtime.totalMemory/maxMemory/freeMemory` - Spoofs runtime memory
3. `/proc/meminfo` reading - Spoofs memory info file
4. `Build` properties - Spoofs device model/manufacturer
5. `SystemProperties` - Spoofs system properties
6. GPU info - Spoofs GPU renderer

### Magisk Module Changes:
- Device model → Samsung Galaxy S10
- Hardware → Exynos 9820
- Heap size → 512m
- OpenGL version

### What This Does NOT Do:
- ❌ No Zram configuration
- ❌ No Swap file creation
- ❌ No LMK tweaks
- ❌ No CPU/GPU optimization
- ❌ No I/O scheduler changes
- ❌ No network optimization

---

## 🎯 Philosophy

**Keep it simple!** This module only spoofs device information to bypass Fortnite's RAM check. No complex performance tweaks that might cause instability.

---

## 📞 Support

If you encounter issues:
1. Check LSPosed logs for "RAMSpoof" messages
2. Verify both modules are installed
3. Make sure you rebooted after installation

---

## 📄 License

Open source for personal use only.

---

**Good luck! 🎮**