@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.xyz.dnieln7.conventions.library.compose)
    alias(libs.plugins.xyz.dnieln7.conventions.codegen)
    alias(libs.plugins.xyz.dnieln7.conventions.hilt)
    alias(libs.plugins.xyz.dnieln7.conventions.testing)
}

android {
    namespace = "xyz.dnieln7.signup"

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

    implementation(project(":domain"))
    implementation(project(":common:composable"))
    implementation(project(":common:coroutines"))
    implementation(project(":common:navigation"))
}