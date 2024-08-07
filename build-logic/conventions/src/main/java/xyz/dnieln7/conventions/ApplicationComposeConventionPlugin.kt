package xyz.dnieln7.conventions

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import xyz.dnieln7.conventions.extension.kotlinOptions
import xyz.dnieln7.conventions.utils.MIN_SDK
import xyz.dnieln7.conventions.utils.TARGET_SDK
import xyz.dnieln7.conventions.utils.enableCompose
import xyz.dnieln7.conventions.utils.excludedResources
import xyz.dnieln7.conventions.utils.javaVersion

class ApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("xyz.dnieln7.conventions.codegen")
                apply("xyz.dnieln7.conventions.hilt")
                apply("xyz.dnieln7.conventions.testing")
            }

            apply(from = "${project.rootDir}/config/detekt/detekt.gradle")

            extensions.configure<ApplicationExtension> {
                enableCompose(this)

                compileSdk = TARGET_SDK

                defaultConfig {
                    minSdk = MIN_SDK
                    targetSdk = TARGET_SDK

                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                compileOptions {
                    sourceCompatibility = javaVersion
                    targetCompatibility = javaVersion
                }

                kotlinOptions {
                    jvmTarget = javaVersion.toString()
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                buildFeatures {
                    buildConfig = true
                }

                packaging {
                    resources {
                        excludes.addAll(excludedResources)
                    }
                }
            }
        }
    }
}