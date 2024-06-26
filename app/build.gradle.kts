

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.pondokdarus"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pondokdarus"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.cardview:cardview:1.0.0")

    // Firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))

    // Firebase Analytics
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Firebase Authentication and Database
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-database:21.0.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0")
}

afterEvaluate {
    tasks.named("mergeDebugResources") {
        // Method 2: Declare an explicit dependency on ':app:processDebugGoogleServices' from ':app:mergeDebugResources' using Task#dependsOn
        dependsOn(tasks.named("processDebugGoogleServices"))

        // Method 3: Declare an explicit dependency on ':app:processDebugGoogleServices' from ':app:mergeDebugResources' using Task#mustRunAfter
        mustRunAfter(tasks.named("processDebugGoogleServices"))
    }
}