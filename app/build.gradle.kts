plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.braindrive"
    compileSdk = rootProject.ext["compile_sdk_version"] as Int

    defaultConfig {
        applicationId = "com.braindrive"
        minSdk = rootProject.ext["min_sdk_version"] as Int
        targetSdk = rootProject.ext["target_sdk_version"] as Int
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    
    flavorDimensions += "game"
    productFlavors {
        create("full") {
            dimension = "game"
            applicationIdSuffix = ""
            versionNameSuffix = ""
            resValue("string", "app_name", "BrainDrive")
        }
        create("mathGame") {
            dimension = "game"
            applicationIdSuffix = ".math"
            versionNameSuffix = "-math"
            resValue("string", "app_name", "Math It")
        }
        create("categorizeEdible") {
            dimension = "game"
            applicationIdSuffix = ".categorize.edible"
            versionNameSuffix = "-edible"
            resValue("string", "app_name", "Categorize - Edible")
        }
        create("categorizeConsumer") {
            dimension = "game"
            applicationIdSuffix = ".categorize.consumer"
            versionNameSuffix = "-consumer"
            resValue("string", "app_name", "Categorize - Consumer")
        }
        create("categorizeHuman") {
            dimension = "game"
            applicationIdSuffix = ".categorize.human"
            versionNameSuffix = "-human"
            resValue("string", "app_name", "Categorize - Human")
        }
        create("memoryGame1") {
            dimension = "game"
            applicationIdSuffix = ".memory.game1"
            versionNameSuffix = "-memory1"
            resValue("string", "app_name", "Memory Game 1")
        }
        create("memoryGame2") {
            dimension = "game"
            applicationIdSuffix = ".memory.game2"
            versionNameSuffix = "-memory2"
            resValue("string", "app_name", "Memory Game 2")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
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
    
    buildFeatures {
        viewBinding true
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.ext["compose_compiler_version"] as String
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Feature modules
    implementation(project(":feature:splash"))
    implementation(project(":feature:home"))
    implementation(project(":feature:games"))
    implementation(project(":feature:settings"))
    
    // Core modules
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:data"))
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
    implementation("androidx.compose.material:material-icons-extended")
    
    // Activity Compose
    implementation("androidx.activity:activity-compose:${rootProject.ext["compose_activity_version"]}")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:${rootProject.ext["compose_navigation_version"]}")
    
    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${rootProject.ext["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${rootProject.ext["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.ext["lifecycle_version"]}")
    
    // Core Android
    implementation("androidx.core:core-ktx:1.15.0")
    
    // Splash Screen API for Android 12+ (SDK 35 compatible)
    implementation("androidx.core:core-splashscreen:${rootProject.ext["splash_screen_version"]}")
    
    // Testing
    testImplementation("junit:junit:${rootProject.ext["junit_version"]}")
    androidTestImplementation("androidx.test.ext:junit:${rootProject.ext["junit_ext_version"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.ext["espresso_version"]}")
    androidTestImplementation(platform("androidx.compose:compose-bom:${rootProject.ext["compose_bom_version"]}"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

