@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.xyz.dnieln7.conventions.application.compose)
}

android {
    namespace = "xyz.dnieln7.justchatting"

    defaultConfig {
        applicationId = "xyz.dnieln7.justchatting"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)

    implementation(platform(libs.io.arrow.kt.arrow.stack))
    implementation(libs.io.arrow.kt.arrow.core)

    implementation(projects.domain)
    implementation(projects.data)
    implementation(projects.framework)
    implementation(projects.common.composable)
    implementation(projects.common.coroutines)
    implementation(projects.common.navigation)
    implementation(projects.features.login)
    implementation(projects.features.signup)
    implementation(projects.features.friendships)
    implementation(projects.features.chats)
    implementation(projects.features.chat)
    implementation(projects.features.profile)
}
