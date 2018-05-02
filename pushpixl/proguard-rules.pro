## Add project specific ProGuard rules here.
## By default, the flags in this file are appended to flags specified
## in /Users/savepanda/android-sdk-macosx/tools/proguard/proguard-android.txt
## You can edit the include path and order by changing the proguardFiles
## directive in build.gradle.
##
## For more details, see
##   http://developer.android.com/guide/developing/tools/proguard.html
#
## Add any project specific keep options here:
#
## If your project uses WebView with JS, uncomment the following
## and specify the fully qualified class name to the JavaScript interface
## class:
##-keepclassmembers class fqcn.of.javascript.interface.for.webview {
##   public *;
##}
#

-dontshrink
#-dontobfuscate

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose


#
## Keep a fixed source file attribute and all line number tables to get line
## numbers in the stack traces.
## You can comment this out if you're not interested in stack traces.
#
#-renamesourcefileattribute SourceFile
#-keepattributes SourceFile,LineNumberTable
#
#

-dontwarn org.apache.http.**
-dontwarn com.android.volley.**

-keepattributes Signature

-keep public class com.google.gson.** {
    public protected *;
    !static !transient <fields>;
    !private <methods>;
    !private static *;
}

-keep class com.neopixl.pushpixl.gcm.PushPixlApplication { *; }

-keep class com.neopixl.pushpixl.network.model.QuietTimeRequest { *; }
-keep class com.neopixl.pushpixl.network.model.Error { *; }
-keep class com.neopixl.pushpixl.network.model.Payload { *; }
-keep class com.neopixl.pushpixl.network.model.Subscription { *; }

-keep class com.neopixl.pushpixl.core.util.PushPixlContext { *; }

-keep public interface com.neopixl.pushpixl.core.RequestListener { *; }
-keep public class com.neopixl.pushpixl.core.NotificationManager {
    public *;
    !static !transient <fields>;
    !private <methods>;
    !private static *;
}
    #!private <fields>;
    #!private <methods>;

-keep public class com.neopixl.pushpixl.core.handler.NotificationHandler {
    public *;
    !private <methods>;
    !private static *;
}
-keep class com.neopixl.pushpixl.gcm.AbstractGCMBroadcastReceiver { *; }
-keep public class com.neopixl.pushpixl.PushPixlConstant { *; }
-keep public interface com.neopixl.pushpixl.core.util.GCMUtilRegistrationListener { *; }

#Util
-keep class com.neopixl.pushpixl.core.util.GCMUtil { *; }
-keep class com.neopixl.pushpixl.core.util.PushPixlPreferences { *; }
-keep class com.neopixl.pushpixl.core.util.DeviceUtil { *; }
-keep class com.neopixl.pushpixl.core.util.PushPixlManifest { *; }

-keep public class com.neopixl.logger.NPLog { *; }

-assumenosideeffects class android.util.Log {
    public static *** e(...);
    public static *** w(...);
    public static *** wtf(...);
    public static *** d(...);
    public static *** v(...);
}

#-assumenosideeffects class com.neopixl.logger.NPLog {
#    public static *** e(...);
#    public static *** w(...);
#    public static *** wtf(...);
#    public static *** d(...);
#    public static *** v(...);
#}

## repackage and optimize
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
#-repackageclasses ""
-optimizationpasses 5
#-overloadaggressively   # disabled
-dontpreverify
#
-obfuscationdictionary keywords.txt
-classobfuscationdictionary keywords.txt
#
-adaptclassstrings **
#-flattenpackagehierarchy ''