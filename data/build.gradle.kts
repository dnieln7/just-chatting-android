import com.android.build.api.dsl.LibraryDefaultConfig
import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.com.google.devtools.ksp)
}

apply<LibraryDefaultConfigPlugin>()

android {
    namespace = "xyz.dnieln7.data"

    defaultConfig {
        setBuildConfigFields(this)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
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

    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.io.arrow.kt.arrow.stack))
    implementation(libs.io.arrow.kt.arrow.core)
    implementation(libs.io.arrow.kt.arrow.core.retrofit)

    ksp(libs.com.squareup.moshi.kotlin.codegen)
    implementation(libs.com.squareup.moshi)
    implementation(libs.com.squareup.retrofit2.converter.moshi)
    implementation(libs.com.squareup.okhttp3.logging.interceptor)

    implementation(libs.androidx.datastore.preferences)

    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)

    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.com.google.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.io.mockk)
    testImplementation(libs.org.amshove.kluent.android)

    implementation(project(":domain"))

    testImplementation(project(":common:testing"))
}

fun setBuildConfigFields(libraryDefaultConfig: LibraryDefaultConfig) {
    val localProperties = Properties()
    localProperties.load(FileInputStream(rootProject.file("local.properties")))

    val justChattingUrl = (localProperties["JUST_CHATTING_URL"] as String?) ?: ""

    libraryDefaultConfig.buildConfigField(
        type = "String",
        name = "JUST_CHATTING_URL",
        value = "\"$justChattingUrl\""
    )
}
