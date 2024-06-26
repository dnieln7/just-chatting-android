@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.xyz.dnieln7.conventions.library)
}

android {
    namespace = "xyz.dnieln7.testing"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.io.mockk)

    implementation(project(":domain"))
}