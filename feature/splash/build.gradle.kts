plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.braindrive.feature.splash"
    compileSdk = rootProject.ext["compile_sdk_version"] as Int

    defaultConfig {
        minSdk = rootProject.ext["min_sdk_version"] as Int
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
    
    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.ext["compose_compiler_version"] as String
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    
    // Hilt
    implementation("com.google.dagger:hilt-android:${rootProject.ext["hilt_version"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.ext["hilt_version"]}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.ext["hilt_navigation_compose_version"]}")
    
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:${rootProject.ext["compose_bom_version"]}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:${rootProject.ext["material3_version"]}")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:${rootProject.ext["compose_navigation_version"]}")
    
    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${rootProject.ext["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${rootProject.ext["lifecycle_version"]}")
    
    // Testing
    testImplementation("junit:junit:${rootProject.ext["junit_version"]}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.ext["coroutines_version"]}")
    androidTestImplementation("androidx.test.ext:junit:${rootProject.ext["junit_ext_version"]}")
}

