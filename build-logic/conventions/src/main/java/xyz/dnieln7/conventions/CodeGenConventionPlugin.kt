package xyz.dnieln7.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project

class CodeGenConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.kapt")
                apply("com.google.devtools.ksp")
            }
        }
    }
}
