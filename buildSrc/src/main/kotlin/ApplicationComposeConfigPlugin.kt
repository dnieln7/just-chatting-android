import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.withType

@Suppress("UnstableApiUsage")
class ApplicationComposeConfigPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.apply("${project.rootDir}/config/detekt/detekt.gradle")

        project.withApplicationExtension {
            compileSdk = TARGET_SDK

            defaultConfig {
                minSdk = MIN_SDK
                targetSdk = TARGET_SDK
            }

            compileOptions {
                sourceCompatibility = javaVersion
                targetCompatibility = javaVersion
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
