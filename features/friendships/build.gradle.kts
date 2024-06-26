@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.xyz.dnieln7.conventions.library.compose)
    alias(libs.plugins.xyz.dnieln7.conventions.code.gen)
    alias(libs.plugins.xyz.dnieln7.conventions.hilt)
}

android {
    namespace = "xyz.dnieln7.friendships"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(platform(libs.io.arrow.kt.arrow.stack))
    implementation(libs.io.arrow.kt.arrow.core)

    implementation(libs.androidx.hilt.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.io.mockk)
    testImplementation(libs.app.cash.turbine)
    testImplementation(libs.org.amshove.kluent.android)

    implementation(project(":domain"))
    implementation(project(":common:composable"))
    implementation(project(":common:coroutines"))
    implementation(project(":common:navigation"))

    testImplementation(project(":common:testing"))
}