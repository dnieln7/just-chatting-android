@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.xyz.dnieln7.conventions.library.compose)
    alias(libs.plugins.xyz.dnieln7.conventions.codegen)
    alias(libs.plugins.xyz.dnieln7.conventions.hilt)
    alias(libs.plugins.xyz.dnieln7.conventions.testing)
}

android {
    namespace = "xyz.dnieln7.friendships"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.io.arrow.kt.arrow.stack))
    implementation(libs.io.arrow.kt.arrow.core)

    implementation(projects.domain)
    implementation(projects.common.composable)
    implementation(projects.common.coroutines)
    implementation(projects.common.navigation)
}