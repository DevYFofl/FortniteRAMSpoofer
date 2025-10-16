# 🎯 What This Project Does (Exactly)

## 📝 Simple Explanation

This project contains **TWO simple modules** that work together to trick Fortnite into thinking your 2GB RAM device is actually an 8GB Samsung Galaxy S10.

**That's it. Nothing more, nothing less.**

---

## 🔧 Module 1: Xposed Module

### What it does:
When Fortnite asks Android "How much RAM do I have?", the Xposed module intercepts that question and lies:

```
Fortnite: "Hey Android, how much RAM is available?"
Android (normally): "2GB"
Xposed Module: "8GB!" (lying)
Fortnite: "Oh great, 8GB! I can run!"
```

### Technical Details:
It hooks (intercepts) these Android functions:
1. `ActivityManager.getMemoryInfo()` - Returns fake 8GB
2. `Runtime.totalMemory()` - Returns fake 8GB
3. `Runtime.maxMemory()` - Returns fake 8GB
4. `Runtime.freeMemory()` - Returns fake 6GB available
5. `/proc/meminfo` reading - Returns fake memory info
6. `Build.MODEL` - Returns "Samsung Galaxy S10"
7. `Build.HARDWARE` - Returns "exynos9820"
8. GPU info - Returns "Mali-G76 MP12"

---

## 🔧 Module 2: Magisk Module

### What it does:
Changes system properties so when Fortnite checks device info, it sees:

```
Device Model: Samsung Galaxy S10 (instead of your real device)
Hardware: Exynos 9820 (instead of your real hardware)
RAM: 8GB (instead of 2GB)
```

### Technical Details:
It modifies these system properties:
- `ro.product.model` → SM-G973F
- `ro.product.manufacturer` → Samsung
- `ro.hardware` → exynos9820
- `dalvik.vm.heapsize` → 512m

---

## ❌ What This Does NOT Do

### NO Performance Tweaks:
- ❌ No Zram configuration
- ❌ No Swap file creation
- ❌ No LMK (Low Memory Killer) tweaks
- ❌ No CPU governor changes
- ❌ No GPU optimization
- ❌ No I/O scheduler changes
- ❌ No network optimization
- ❌ No memory management tweaks

### Why?
Because you specifically asked for **ONLY device info spoofing**, no performance modifications.

---

## 🎮 How It Works Together

```
1. You install both modules
2. You reboot your device
3. Fortnite launches and checks:
   - "What device is this?" → Magisk says: "Samsung Galaxy S10"
   - "How much RAM?" → Xposed says: "8GB"
4. Fortnite: "Great! This device is supported!"
5. Game launches successfully
```

---

## 📊 What You Get

### ✅ Pros:
- Fortnite will launch (bypasses RAM check)
- Simple and clean (no complex tweaks)
- Less chance of system instability
- Easy to uninstall

### ⚠️ Cons:
- Performance will still be limited (you actually have 2GB)
- FPS will be low (20-30)
- May lag in crowded areas
- Must use low graphics settings

---

## 🤔 Why Two Modules?

### Xposed Module:
- Intercepts function calls in real-time
- Lies about RAM when Fortnite asks
- Works at the app level

### Magisk Module:
- Changes system properties permanently
- Makes the device "look like" a Galaxy S10
- Works at the system level

**Both are needed** because Fortnite checks device info in multiple ways.

---

## 🔍 How to Verify It's Working

### Check Xposed Module:
1. Open LSPosed Manager
2. Go to Logs
3. Look for messages like:
   ```
   RAMSpoof: Hooking com.epicgames.fortnite
   RAMSpoof: Spoofed MemoryInfo to 8GB
   RAMSpoof: Spoofed Build properties
   ```

### Check Magisk Module:
```bash
# In terminal
getprop ro.product.model
# Should show: SM-G973F

getprop ro.hardware
# Should show: exynos9820
```

---

## 💡 Important Notes

### This is JUST spoofing:
- Your device still has 2GB RAM physically
- The system still has 2GB RAM
- Only Fortnite "thinks" you have 8GB
- Other apps see real 2GB

### Performance:
- You're running a heavy game on weak hardware
- Expect low FPS (20-30)
- Expect occasional lag
- Must use lowest graphics settings

### Safety:
- This is safe (just lying about device info)
- No dangerous system modifications
- Easy to remove if needed
- Won't damage your device

---

## 🎯 Summary

**In one sentence:**
This project makes Fortnite think your 2GB phone is an 8GB Samsung Galaxy S10, so it lets you play the game.

**That's all it does. Nothing more.**

---

## 📦 Files Included

```
FortniteRAMSpoofer/
├── app/                          # Xposed Module source
│   ├── src/main/java/...        # Java code that does the spoofing
│   ├── AndroidManifest.xml      # App manifest
│   └── build.gradle             # Build configuration
├── magisk/                       # Magisk Module
│   ├── module.prop              # Module info
│   ├── system.prop              # System property changes
│   ├── install.sh               # Installation script
│   └── FortniteSpoofer.zip      # Ready-to-install ZIP
├── README.md                     # English documentation
├── INSTALL_AR.md                 # Arabic installation guide
└── WHAT_THIS_DOES.md            # This file
```

---

**Questions? Read the README.md or INSTALL_AR.md files!**