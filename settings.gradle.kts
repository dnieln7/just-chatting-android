pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Just Chatting"

include(":app")

include(":data")
include(":domain")

include(":common:composable")
include(":features:signup")
include(":common:navigation")
include(":features:login")
include(":common:testing")
include(":framework")
include(":common:coroutines")
