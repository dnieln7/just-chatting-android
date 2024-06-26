package xyz.dnieln7.conventions.utils

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import xyz.dnieln7.conventions.extension.libs

fun Project.enableCompose(commonExtension: CommonExtension<*, *, *, *, *>) {
    with(commonExtension) {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = KOTLIN_COMPILER_EXTENSION_VERSION
        }

        dependencies {
            "implementation"(platform(libs.findLibrary("androidx.compose.bom").get()))
            "implementation"(libs.findLibrary("androidx.compose.ui").get())
            "implementation"(libs.findLibrary("androidx.compose.ui.graphics").get())
            "implementation"(libs.findLibrary("androidx.compose.ui.tooling.preview").get())
            "implementation"(libs.findLibrary("androidx.compose.material3").get())
            "implementation"(libs.findLibrary("androidx.compose.material.icons.extended").get())
            "implementation"(libs.findLibrary("androidx.compose.material").get())

            "androidTestImplementation"(platform(libs.findLibrary("androidx.compose.bom").get()))
            "androidTestImplementation"(libs.findLibrary("androidx.compose.ui.test.junit4").get())
            "debugImplementation"(libs.findLibrary("androidx.compose.ui.tooling").get())
            "debugImplementation"(libs.findLibrary("androidx.compose.ui.test.manifest").get())
        }
    }
}
