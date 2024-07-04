package xyz.dnieln7.conventions.extension

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal val Project.libs get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            block(this)
        }
    }
}

internal fun Project.kapt(block: KaptExtension.() -> Unit) {
    extensions.configure<KaptExtension> {
        block(this)
    }
}
