import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("UnstableApiUsage")
class LibraryDefaultConfigPlugin : Plugin<Project> {

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
                buildConfig = true
            }
        }
    }
}
