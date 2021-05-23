package io.github.warahiko.shoppingmemoapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ErrorDialogFragment : DialogFragment() {

    private val errorMessage: String by lazy {
        checkNotNull(arguments?.getString(ARG_ERROR_MESSAGE))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setContent {
            ErrorDialog(
                message = errorMessage,
                onDismiss = {
                    this@ErrorDialogFragment.dismiss()
                },
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(errorMessage: String) =
            ErrorDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ERROR_MESSAGE, errorMessage)
                }
            }

        const val TAG = "ErrorDialogFragment"
        private const val ARG_ERROR_MESSAGE = "errorMessage"
    }
}
