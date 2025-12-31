plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.braindrive.core.ui"
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
    
    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.ext["compose_compiler_version"] as String
    }
}

dependencies {
    implementation(project(":core:common"))
    
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
    
    // Testing
    testImplementation("junit:junit:${rootProject.ext["junit_version"]}")
    androidTestImplementation("androidx.test.ext:junit:${rootProject.ext["junit_ext_version"]}")
    androidTestImplementation(platform("androidx.compose:compose-bom:${rootProject.ext["compose_bom_version"]}"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

