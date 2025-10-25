plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp") version "2.0.0-1.0.23"
}

android {
    namespace = "edu.ucne.jugadorestictactoe"
    compileSdk = 36

    defaultConfig {
        applicationId = "edu.ucne.jugadorestictactoe"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "TIC_TAC_TOE_BASE_URL",
            "\"https://gestionhuacalesapi.azurewebsites.net/\""
        )
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation("com.squareup.retrofit2:retrofit:2.11.0") {
        exclude(group = "com.google.auto.value")
    }
    implementation("com.squareup.okhttp3:okhttp:4.12.0") {
        exclude(group = "com.google.auto.value")
    }
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") {
        exclude(group = "com.google.auto.value")
    }
    implementation("com.squareup.retrofit2:converter-scalars:2.11.0") {
        exclude(group = "com.google.auto.value")
    }
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0") {
        exclude(group = "com.google.auto.value")
    }
    implementation(libs.hilt.android)
    implementation(libs.androidx.compose.foundation)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.room.runtime) {
        exclude(group = "com.google.auto.value")
    }
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation("androidx.work:work-runtime-ktx:2.9.1") {
        exclude(group = "com.google.auto.value")
    }
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("com.google.dagger:hilt-android:2.52")
    ksp("com.google.dagger:hilt-compiler:2.52")
    implementation("androidx.hilt:hilt-work:1.2.0")
    ksp("androidx.hilt:hilt-compiler:1.2.0")

}