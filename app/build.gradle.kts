import com.android.build.api.dsl.ApplicationDefaultConfig
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
}

android {
    namespace = "xyz.dnieln7.justchatting"
    compileSdk = 34

    defaultConfig {
        applicationId = "xyz.dnieln7.justchatting"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        setBuildConfigFields()

        vectorDrawables {
            useSupportLibrary = true
        }
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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    implementation(platform(libs.io.arrow.kt.arrow.stack))
    implementation(libs.io.arrow.kt.arrow.core)

    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.com.google.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    ksp(libs.com.squareup.moshi.kotlin.codegen)
    implementation(libs.com.squareup.moshi)
    implementation(libs.com.squareup.retrofit2.converter.moshi)
    implementation(libs.com.squareup.okhttp3.logging.interceptor)

    implementation(libs.androidx.datastore.preferences)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.io.mockk)
    testImplementation(libs.app.cash.turbine)
    testImplementation(libs.org.amshove.kluent.android)
}

fun ApplicationDefaultConfig.setBuildConfigFields() {
    val localProperties = Properties()
    localProperties.load(FileInputStream(rootProject.file("local.properties")))

    val justChattingUrl = (localProperties["JUST_CHATTING_URL"] as String?) ?: ""

    buildConfigField(
        type = "String",
        name = "JUST_CHATTING_URL",
        value = "\"$justChattingUrl\""
    )
}
