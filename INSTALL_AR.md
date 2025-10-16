# 📱 دليل التثبيت السريع

## 🎯 ما هذا المشروع؟

موديولات بسيطة لتزييف معلومات الجهاز فقط - **بدون أي تحسينات أو Zram أو Swap**

---

## 📦 المحتويات

1. **Xposed Module** - يزيف معلومات الذاكرة والجهاز
2. **Magisk Module** - يزيف خصائص النظام

---

## 🔧 المتطلبات

```
✅ Android 7.0+
✅ Root (Magisk)
✅ LSPosed أو EdXposed
✅ جهاز 2GB RAM
```

---

## 📥 التثبيت

### الخطوة 1: Xposed Module

#### الطريقة الأولى: بناء من المصدر
```bash
cd FortniteRAMSpoofer
./gradlew assembleRelease
```
الملف سيكون في: `app/build/outputs/apk/release/app-release.apk`

#### الطريقة الثانية: استخدام Android Studio
1. افتح المشروع في Android Studio
2. Build → Build Bundle(s) / APK(s) → Build APK(s)
3. ثبت الـ APK على جهازك

#### بعد التثبيت:
1. افتح LSPosed Manager
2. فعّل "Fortnite RAM Spoofer"
3. اختر التطبيقات المستهدفة:
   - `com.epicgames.fortnite`
   - `kbi.ntjgzgmei.mkqmbnhc` (إذا كنت تستخدم نسخة معدلة)
4. أعد تشغيل الجهاز

---

### الخطوة 2: Magisk Module

الملف جاهز: `magisk/FortniteSpoofer.zip`

#### التثبيت:
1. افتح Magisk Manager
2. اذهب إلى Modules
3. اضغط "Install from storage"
4. اختر `FortniteSpoofer.zip`
5. أعد تشغيل الجهاز

---

## 🎮 الاستخدام

1. تأكد من تثبيت الموديولين
2. أعد تشغيل الجهاز
3. شغّل Fortnite
4. اخفض إعدادات الجرافيك:
   - Quality: Low
   - 3D Resolution: 50%
   - FPS: 30

---

## ❓ حل المشاكل

### رسالة "3GB RAM Required"

```bash
# 1. تحقق من تفعيل Xposed Module في LSPosed
# 2. امسح بيانات اللعبة
pm clear com.epicgames.fortnite
# 3. أعد تشغيل الجهاز
```

### اللعبة تتوقف

1. اخفض إعدادات الجرافيك أكثر
2. أغلق جميع التطبيقات الأخرى
3. أعد تشغيل الجهاز

---

## ⚠️ ملاحظات مهمة

### ✅ ما يفعله هذا المشروع:
- تزييف معلومات الذاكرة (8GB)
- تزييف معلومات الجهاز (Samsung Galaxy S10)
- تزييف معلومات الهاردوير

### ❌ ما لا يفعله:
- **لا يوجد Zram**
- **لا يوجد Swap**
- **لا توجد تحسينات LMK**
- **لا توجد تحسينات CPU/GPU**
- **لا توجد تحسينات I/O**

**هذا تزييف بسيط فقط!**

---

## 📊 النتائج المتوقعة

| الميزة | النتيجة |
|--------|---------|
| تشغيل اللعبة | ✅ نعم |
| FPS | 20-30 |
| الجرافيك | منخفض |
| الاستقرار | متوسط |

---

## 🔍 التحقق من التثبيت

### تحقق من Xposed Module:
1. افتح LSPosed Manager
2. اذهب إلى Logs
3. ابحث عن "RAMSpoof"
4. يجب أن ترى رسائل مثل:
   - "RAMSpoof: Hooking com.epicgames.fortnite"
   - "RAMSpoof: Spoofed MemoryInfo to 8GB"

### تحقق من Magisk Module:
```bash
# تحقق من الخصائص
getprop ro.product.model
# يجب أن يظهر: SM-G973F

getprop ro.hardware
# يجب أن يظهر: exynos9820
```

---

## 📞 الدعم

إذا واجهت مشاكل:
1. تحقق من Logs في LSPosed
2. تأكد من تثبيت الموديولين
3. تأكد من إعادة تشغيل الجهاز

---

## ⚠️ تحذير

- هذا الحل تجريبي
- استخدمه على مسؤوليتك
- قد يلغي ضمان الجهاز
- الأداء سيكون أقل من أجهزة 4GB+

---

**حظاً موفقاً! 🎮**