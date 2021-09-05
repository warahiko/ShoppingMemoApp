package io.github.warahiko.shoppingmemoapp.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.warahiko.shoppingmemoapp.R

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(192.dp)
                .align(Alignment.Center),
        )
    }
}
