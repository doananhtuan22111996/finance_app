package vn.finance.app.pages.routing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

/**
Follow by: https://developer.android.com/develop/ui/views/launch/splash-screen/migrate
SDK < 31: We need to RoutingActivity to transform splash theme to application theme
SDK >= 31: postSplashScreenTheme will handle it, too
 */
class RoutingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        startActivity(Intent(this, RootActivity::class.java))
        finish()
    }
}