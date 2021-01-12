-dontwarn com.android.installreferrer.api.**
-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**
-dontwarn com.squareup.okhttp.**
-dontwarn com.crashlytics.android.answers.shim.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class com.facebook.ads.** { *; }
-dontwarn com.facebook.ads.**
-keep class com.facebook.** { *; }
-dontwarn com.facebook.**
-dontwarn com.google.firebase.appindexing.**

-keepattributes *Annotation*,SourceFile,LineNumberTable

-ignorewarnings

-keep class * {
    public private *;
}