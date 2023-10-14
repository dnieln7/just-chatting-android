import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Project

fun Project.withApplicationExtension(block: ApplicationExtension.() -> Unit) {
    val extension = extensions.getByName(
        ANDROID_COMPONENTS_EXTENSION
    ) as ApplicationAndroidComponentsExtension

    extension.finalizeDsl {
        block(it)
    }
}

fun Project.withLibraryExtension(block: LibraryExtension.() -> Unit) {
    val extension = extensions.getByName(
        ANDROID_COMPONENTS_EXTENSION
    ) as LibraryAndroidComponentsExtension

    extension.finalizeDsl {
        block(it)
    }
}

private const val ANDROID_COMPONENTS_EXTENSION = "androidComponents"
