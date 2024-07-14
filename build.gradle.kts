// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.5.0")
        classpath ("com.google.gms:google-services:4.3.10")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")

    }

}




plugins {
    id("com.android.application") version "8.5.0" apply false
    id("com.android.library") version "8.5.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
