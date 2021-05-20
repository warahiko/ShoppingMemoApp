package io.github.warahiko.shoppingmemoapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setContent {
            // TODO: 仮置き
            val items = listOf(
                ShoppingItem(id = UUID.randomUUID(), name = "にんじん", 1),
                ShoppingItem(id = UUID.randomUUID(), name = "たまねぎ", 1),
                ShoppingItem(id = UUID.randomUUID(), name = "卵", 1),
                ShoppingItem(id = UUID.randomUUID(), name = "牛乳", 3),
            )
            HomeScreen(items)
        }
    }
}
