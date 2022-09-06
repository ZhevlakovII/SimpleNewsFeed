package com.izhxx.simplenewsfeed.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.core.view.isVisible

class LayoutSwapper {
    companion object {
        fun swapper(show: View, hide: View, duration: Int = 500) {
            if (!show.isVisible) {
                hide.visibility = View.VISIBLE

                show.fade(duration)

                hide.animate()
                    .alpha(0F)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            hide.visibility = View.GONE
                        }
                    })
                    .duration = duration.toLong()
            }
        }
    }
}

fun View.fade(duration: Int) {
    this.apply {
        alpha = 0F
        visibility = View.VISIBLE

        animate()
            .alpha(1F)
            .setListener(null)
            .duration = duration.toLong()
    }
}