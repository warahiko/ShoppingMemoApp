package io.github.warahiko.shoppingmemoapp.error

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import io.github.warahiko.shoppingmemoapp.ui.Event
import io.github.warahiko.shoppingmemoapp.ui.common.ErrorDialogFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorMonitor @Inject constructor(
    private val errorHolder: ErrorHolder,
) : Application.ActivityLifecycleCallbacks {
    private val jobs: MutableMap<FragmentActivity, Job> = mutableMapOf()

    private fun onError(activity: FragmentActivity, error: Event<AppError>) {
        val errorContent = error.getContentIfNotHandled() ?: return
        val fragmentManager = activity.supportFragmentManager

        when (errorContent) {
            is NetworkError -> {
                if (fragmentManager.findFragmentByTag(ErrorDialogFragment.TAG) != null) return
                val message = activity.getString(errorContent.errorMessage)
                ErrorDialogFragment.newInstance(message)
                    .show(fragmentManager, ErrorDialogFragment.TAG)
            }
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {
        if (activity is FragmentActivity) {
            jobs[activity] = activity.lifecycleScope.launch {
                errorHolder.error.collect {
                    onError(activity, it)
                }
            }
        }
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity is FragmentActivity) {
            jobs[activity]?.cancel()
        }
    }

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}
