import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serializable)
}

android {
    namespace = "com.cvopa.peter.scratchy"
    compileSdk = 36

    buildFeatures {
        compose =  true
    }

    defaultConfig {
        applicationId = "com.cvopa.peter.scratchy"
        minSdk = 27
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin { compilerOptions { jvmTarget = JvmTarget.JVM_17 } }
}

dependencies {
    //local modules
    implementation(project(":core"))

    //core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // timber
    implementation(libs.timber)

    // lc
    implementation(libs.lifecycle.compose)
    implementation(libs.lifecycle.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.savestate)

    // dagger
    implementation(libs.dagger.base)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)

    // navigation
    implementation(libs.navigation.compose)

    // hilt
    implementation(libs.hilt)
    implementation(libs.hilt.compose)
    implementation(libs.androidx.hilt.common)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.lifecycle)

    // compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material)

    // serialization
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    // testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}