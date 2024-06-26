package xyz.dnieln7.conventions

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import xyz.dnieln7.conventions.utils.enableCompose

class LibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("xyz.dnieln7.conventions.library")
            }

            extensions.configure<LibraryExtension> {
                enableCompose(this)
            }
        }
    }
}
