#!/system/bin/sh

SKIPUNZIP=1

ui_print "╔═══════════════════════════════════════╗"
ui_print "║   Fortnite Device Spoofer Installer  ║"
ui_print "║   تزييف معلومات الجهاز فقط           ║"
ui_print "╚═══════════════════════════════════════╝"
ui_print ""

ui_print "- استخراج الملفات..."
unzip -o "$ZIPFILE" 'system.prop' -d $MODPATH >&2

ui_print "- تعيين الصلاحيات..."
set_perm_recursive $MODPATH 0 0 0755 0644

ui_print ""
ui_print "✓ تم التثبيت بنجاح!"
ui_print ""
ui_print "ملاحظات:"
ui_print "1. أعد تشغيل الجهاز"
ui_print "2. تأكد من تثبيت Xposed Module"
ui_print "3. فعّل الموديول في LSPosed"
ui_print ""
ui_print "هذا الموديول يزيف معلومات الجهاز فقط"
ui_print "بدون أي تحسينات أو تعديلات أخرى"
ui_print ""