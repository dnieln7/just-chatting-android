package xyz.dnieln7.conventions

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import xyz.dnieln7.conventions.extension.kotlinOptions
import xyz.dnieln7.conventions.utils.MIN_SDK
import xyz.dnieln7.conventions.utils.TARGET_SDK
import xyz.dnieln7.conventions.utils.excludedResources
import xyz.dnieln7.conventions.utils.javaVersion

class LibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                compileSdk = TARGET_SDK

                defaultConfig {
                    minSdk = MIN_SDK
                    targetSdk = TARGET_SDK

                    consumerProguardFiles("consumer-rules.pro")
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
