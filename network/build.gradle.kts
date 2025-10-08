import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.cvopa.peter.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 27
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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

    // core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // moshi
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi.base)
    implementation(libs.moshi.kt)
    implementation(libs.moshi.adapters)

    // timber
    implementation(libs.timber)

    // dagger
    implementation(libs.dagger.base)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)

    // hilt
    implementation(libs.hilt)
    implementation(libs.hilt.compose)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.lifecycle)

    // retrofit
    implementation(libs.retrofit.moshi)
    api(libs.retrofit.base)
    implementation(libs.okhttp.interceptor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
