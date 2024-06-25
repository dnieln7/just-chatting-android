pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    includeBuild("build-logic")
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
include(":features:friendships")
include(":features:chats")
include(":features:profile")
include(":features:chat")
