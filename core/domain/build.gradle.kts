plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.braindrive.core.domain"
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
    // Dependency Injection
    implementation("javax.inject:javax.inject:1") // <--- Add this line

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.ext["coroutines_version"]}")
    
    // Testing
    testImplementation("junit:junit:${rootProject.ext["junit_version"]}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.ext["coroutines_version"]}")
    testImplementation("org.mockito:mockito-core:${rootProject.ext["mockito_version"]}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${rootProject.ext["mockito_kotlin_version"]}")
}

