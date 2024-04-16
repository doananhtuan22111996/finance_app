package vn.finance.app.pages.routing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.finance.app.pages.main.MainActivity
import vn.finance.app.utils.Constants

/**
Follow by: https://developer.android.com/develop/ui/views/launch/splash-screen/migrate
SDK < 31: We need to RoutingActivity to transform splash theme to application theme
SDK >= 31: postSplashScreenTheme will handle it, too
 */
class RoutingActivity : AppCompatActivity() {
    private val viewModel: RoutingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        onBindViewModel()
        viewModel.onSkipped()
    }

    private fun onBindViewModel() {
        viewModel.skipped.observe(this) { skipped ->
            skipped?.run {
                val intent = Intent(this@RoutingActivity, MainActivity::class.java)
                if (this) {
                    // TODO check logged in -> navigate to home
                    intent.putExtra(
                        Constants.KEY_ROUTING_NAME, RootEnum.Login.hashCode()
                    )
                } else {
                    intent.putExtra(
                        Constants.KEY_ROUTING_NAME, RootEnum.OnBoarding.hashCode()
                    )
                }
                startActivity(intent)
                finish()
            }

        }
    }
}