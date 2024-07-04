package xyz.dnieln7.conventions.utils

import org.gradle.api.JavaVersion

const val MIN_SDK = 26
const val TARGET_SDK = 34
const val KOTLIN_COMPILER_EXTENSION_VERSION = "1.4.3"

val javaVersion = JavaVersion.VERSION_17

val excludedResources = listOf(
    "/META-INF/{AL2.0,LGPL2.1}",
    "META-INF/LICENSE.md",
    "META-INF/LICENSE-notice.md",
)
