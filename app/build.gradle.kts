plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.lordrush.calculator"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.lordrush.calculator"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.mlkit:text-recognition:16.0.0")
}
