@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.xyz.dnieln7.conventions.library.compose)
    alias(libs.plugins.xyz.dnieln7.conventions.testing)
}

android {
    namespace = "xyz.dnieln7.composable"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(projects.domain)
}