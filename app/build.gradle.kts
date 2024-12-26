plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id ("com.google.devtools.ksp") version "2.1.0-1.0.28"
}

android {
    namespace = "uz.turgunboyevjurabek.sharedanimatedapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "uz.turgunboyevjurabek.sharedanimatedapp"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    /**
     * Navigation
     */
    implementation(libs.androidx.compose.navigation)

    /**
     * Kotlin serialization
     */
    implementation(libs.kotlinx.serialization.json)

    /**
     * ViewModel
     */
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    /**
     * Kotlin Coroutines
     */
    implementation(libs.kotlinx.coroutines.android)

    /**
     * Room database
     */
    implementation (libs.androidx.room.runtime)
    ksp (libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    /**
     * Coil
     */
    implementation(libs.coil) // Core library
    implementation(libs.coil.compose) // for Jetpack Compose

    /**
     * Gson
     */
    implementation (libs.gson)  // For serialization

    /**
     * SystemUIController
     */
    implementation (libs.accompanist.systemuicontroller)

    /**
     * Koin
     */
    // Koin BOM (Bill of Materials) kutubxonasi
    implementation(platform("io.insert-koin:koin-bom:4.0.0"))

    // Koin uchun asosiy kutubxona
    implementation("io.insert-koin:koin-core")

    // Android va Jetpack Compose qo'llab-quvvatlashi
    implementation("io.insert-koin:koin-android")
    implementation("io.insert-koin:koin-androidx-compose")

    /**
     * Ktor
     */
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation (libs.ktor.client.serialization)
    // Content Negotiation uchun
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    // Logging uchun
    implementation(libs.ktor.client.logging)

}