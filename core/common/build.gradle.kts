plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.braindrive.core.common"
    compileSdk = rootProject.ext["compile_sdk_version"] as Int

    defaultConfig {
        minSdk = rootProject.ext["min_sdk_version"] as Int
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Hilt
    implementation("com.google.dagger:hilt-android:${rootProject.ext["hilt_version"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.ext["hilt_version"]}")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.ext["coroutines_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.ext["coroutines_version"]}")
    
    // DataStore
    implementation("androidx.datastore:datastore-preferences:${rootProject.ext["datastore_version"]}")
    
    // Testing
    testImplementation("junit:junit:${rootProject.ext["junit_version"]}")
    androidTestImplementation("androidx.test.ext:junit:${rootProject.ext["junit_ext_version"]}")
}

