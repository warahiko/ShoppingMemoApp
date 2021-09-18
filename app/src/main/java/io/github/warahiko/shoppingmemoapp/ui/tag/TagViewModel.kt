package io.github.warahiko.shoppingmemoapp.ui.tag

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    launchSafe: LaunchSafe,
) : ViewModel(), LaunchSafe by launchSafe
