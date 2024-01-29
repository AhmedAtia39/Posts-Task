package sa.com

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import dagger.hilt.android.AndroidEntryPoint
import sa.com.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mAppBarConfig: AppBarConfiguration
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavHost:NavHostFragment
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initNav()
    }

    private fun initNav() {
        mNavHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        mNavController = mNavHost.navController
        mAppBarConfig = AppBarConfiguration(mNavController.graph)
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp(mAppBarConfig) || super.onSupportNavigateUp()
    }
}