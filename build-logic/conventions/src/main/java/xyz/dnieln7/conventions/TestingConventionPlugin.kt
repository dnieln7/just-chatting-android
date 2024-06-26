package xyz.dnieln7.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import xyz.dnieln7.conventions.extension.libs

class TestingConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "testImplementation"(libs.findLibrary("junit").get())
                "androidTestImplementation"(libs.findLibrary("androidx.test.ext.junit").get())
                "androidTestImplementation"(libs.findLibrary("androidx.test.espresso.core").get())

                "testImplementation"(
                    libs.findLibrary("org.jetbrains.kotlinx.coroutines.test").get()
                )
                "testImplementation"(libs.findLibrary("io.mockk").get())
                "testImplementation"(libs.findLibrary("app.cash.turbine").get())
                "testImplementation"(libs.findLibrary("org.amshove.kluent.android").get())

                "testImplementation"(project(":common:testing"))
            }
        }
    }
}