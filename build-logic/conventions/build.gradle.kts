plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.com.android.tools.build.gradle)
    compileOnly(libs.org.jetbrains.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("ApplicationComposeConventionPlugin") {
            id = "xyz.dnieln7.conventions.application-compose"
            implementationClass = "xyz.dnieln7.conventions.ApplicationComposeConventionPlugin"
        }
    }
}
