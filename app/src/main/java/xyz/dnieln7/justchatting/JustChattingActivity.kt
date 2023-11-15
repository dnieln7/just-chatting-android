package xyz.dnieln7.justchatting

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import xyz.dnieln7.composable.theme.JCTheme
import xyz.dnieln7.justchatting.navigation.HomeDestination
import xyz.dnieln7.justchatting.navigation.JustChattingNavHost
import xyz.dnieln7.login.navigation.LoginDestination

@AndroidEntryPoint
class JustChattingActivity : ComponentActivity() {

    private val justChattingViewModel by viewModels<JustChattingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setOnExitAnimationListener {
            ObjectAnimator.ofFloat(
                it.view,
                View.TRANSLATION_Y,
                0f,
                it.view.height.toFloat()
            ).apply {
                interpolator = OvershootInterpolator()
                duration = SPLASH_SCREEN_DURATION_MILLIS

                doOnEnd { _ -> it.remove() }
            }.start()
        }

        setContent {
            JCTheme {
                val uiState by justChattingViewModel.state.collectAsStateWithLifecycle()

                splashScreen.setKeepOnScreenCondition {
                    uiState == JustChattingViewModel.InitializedState.Loading
                }

                when (uiState) {
                    JustChattingViewModel.InitializedState.Loading -> CircularProgressIndicator()

                    JustChattingViewModel.InitializedState.Authenticated -> JustChattingNavHost(
                        startDestination = HomeDestination.route
                    )

                    JustChattingViewModel.InitializedState.NotAuthenticated -> JustChattingNavHost(
                        startDestination = LoginDestination.route
                    )
                }
            }
        }
    }
}

private const val SPLASH_SCREEN_DURATION_MILLIS = 500L
