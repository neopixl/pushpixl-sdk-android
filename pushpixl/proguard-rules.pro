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

-keep class com.neopixl.pushpixl.network.model.* { *; }
-keep class com.neopixl.pushpixl.model.* { *; }
-keep class com.neopixl.pushpixl.listener.* { *; }

-keep class com.neopixl.pushpixl.PushpixlManager { *; }
-keep public class com.neopixl.pushpixl.PushPixlConstant { *; }



-dontwarn org.apache.http.**
-dontwarn com.android.volley.**

-keepattributes Signature

-keep public class com.google.gson.** {
    public protected *;
    !static !transient <fields>;
    !private <methods>;
    !private static *;
}

-assumenosideeffects class android.util.Log {
    public static *** e(...);
    public static *** w(...);
    public static *** wtf(...);
    public static *** d(...);
    public static *** v(...);
}

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
