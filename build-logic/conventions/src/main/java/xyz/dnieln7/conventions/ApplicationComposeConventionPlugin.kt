package xyz.dnieln7.conventions

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import xyz.dnieln7.conventions.extension.kotlinOptions
import xyz.dnieln7.conventions.utils.KOTLIN_COMPILER_EXTENSION_VERSION
import xyz.dnieln7.conventions.utils.MIN_SDK
import xyz.dnieln7.conventions.utils.TARGET_SDK
import xyz.dnieln7.conventions.utils.excludedResources
import xyz.dnieln7.conventions.utils.javaVersion

class ApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("xyz.dnieln7.conventions.code-gen")
                apply("xyz.dnieln7.conventions.hilt")
            }

            apply(from = "${project.rootDir}/config/detekt/detekt.gradle")

            extensions.configure<ApplicationExtension> {
                compileSdk = TARGET_SDK

                defaultConfig {
                    minSdk = MIN_SDK
                    targetSdk = TARGET_SDK

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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
                    compose = true
                    buildConfig = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = KOTLIN_COMPILER_EXTENSION_VERSION
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