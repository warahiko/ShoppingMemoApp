package io.github.warahiko.shoppingmemoapp

import android.app.ActivityManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoNavHost
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRecentAppsAppearance()

        setContent {
            ShoppingMemoAppTheme {
                ShoppingMemoNavHost()
            }
        }
    }

    private fun setRecentAppsAppearance() {
        @Suppress("Deprecation")
        val taskDescription = ActivityManager.TaskDescription(
            null,
            null,
            MaterialColors.getColor(this, R.attr.colorPrimarySurface, 0),
        )
        setTaskDescription(taskDescription)
    }
}
