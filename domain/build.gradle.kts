@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.xyz.dnieln7.conventions.library)
    alias(libs.plugins.xyz.dnieln7.conventions.codegen)
    alias(libs.plugins.xyz.dnieln7.conventions.hilt)
    alias(libs.plugins.xyz.dnieln7.conventions.testing)
}

android {
    namespace = "xyz.dnieln7.domain"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.io.arrow.kt.arrow.stack))
    implementation(libs.io.arrow.kt.arrow.core)
}