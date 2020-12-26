package com.dansdev.app.keyboard

import android.util.Log
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Call into onCreateView override method
 *
 * for correct work of this utils, please setup activity resize configuration
 *
 * android:windowSoftInputMode="adjustResize"
 */
fun Fragment.handleKeyboardStatus(listener: OnVisibilityKeyboardChange) {
    val rootView = activity?.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
    val viewGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
        rootView?.let { rootView ->
            val height = rootView.keyboardHeight()
            if (lifecycle.currentState == Lifecycle.State.RESUMED) listener.onVisibilityKeyboardChange(
                height > 100,
                height
            )
            else Log.e(
                javaClass.simpleName,
                "Try to handle keyboard in destroy fragment ${this.javaClass.simpleName}"
            )
        } ?: Log.e(javaClass.simpleName, "Activity is NULL")
    }

    viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            when (event) {
                Lifecycle.Event.ON_DESTROY -> onDestroy(requireActivity(), viewGlobalListener)
            }
        }
    })

    handleKeyboardChanged(
        rootView,
        viewGlobalListener
    )
}

private fun ViewGroup.keyboardHeight(): Int {
    return resources.displayMetrics.heightPixels - height
}

private fun handleKeyboardChanged(
    rootView: ViewGroup?,
    viewGlobalListener: ViewTreeObserver.OnGlobalLayoutListener
) {
    rootView?.let {
        rootView.viewTreeObserver.addOnGlobalLayoutListener(viewGlobalListener)
    }
}

private fun onDestroy(
    activity: FragmentActivity,
    viewGlobalListener: ViewTreeObserver.OnGlobalLayoutListener
) {
    activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)?.let { rootView ->
        rootView.viewTreeObserver.removeOnGlobalLayoutListener(viewGlobalListener)
    }
}
