import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("UnstableApiUsage")
class LibraryComposeConfigPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.withLibraryExtension {
            compileSdk = TARGET_SDK

            defaultConfig {
                minSdk = MIN_SDK
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
        }
    }
}
