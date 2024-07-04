package xyz.dnieln7.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import xyz.dnieln7.conventions.extension.kapt
import xyz.dnieln7.conventions.extension.libs

class HiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
            }

            kapt {
                correctErrorTypes = true
            }

            dependencies {
                "kapt"(libs.findLibrary("com.google.dagger.hilt.android.compiler").get())
                "implementation"(libs.findLibrary("com.google.dagger.hilt.android").get())
            }
        }
    }
}
