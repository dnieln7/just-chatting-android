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
        register("LibraryConventionPlugin") {
            id = "xyz.dnieln7.conventions.library"
            implementationClass = "xyz.dnieln7.conventions.LibraryConventionPlugin"
        }
        register("CodeGenConventionPlugin") {
            id = "xyz.dnieln7.conventions.code-gen"
            implementationClass = "xyz.dnieln7.conventions.CodeGenConventionPlugin"
        }
        register("HiltConventionPlugin") {
            id = "xyz.dnieln7.conventions.hilt"
            implementationClass = "xyz.dnieln7.conventions.HiltConventionPlugin"
        }
    }
}
