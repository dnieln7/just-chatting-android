@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
}

apply<LibraryDefaultConfigPlugin>()

android {
    namespace = "xyz.dnieln7.domain"

    defaultConfig {
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

    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.io.arrow.kt.arrow.stack))
    implementation(libs.io.arrow.kt.arrow.core)

    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.com.google.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.io.mockk)
    testImplementation(libs.org.amshove.kluent.android)

    testImplementation(project(":common:testing"))
}