package io.github.warahiko.shoppingmemoapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoNavHost
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingMemoAppTheme {
                ShoppingMemoNavHost()
            }
        }
    }
}
