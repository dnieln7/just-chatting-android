import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("UnstableApiUsage")
class ApplicationDefaultConfigPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.withApplicationExtension {
            compileSdk = TARGET_SDK

            defaultConfig {
                minSdk = MIN_SDK
                targetSdk = TARGET_SDK
            }
        }
    }
}
