package com.example.mytube_test

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout


class CustomMotionLayout(context: Context, attributeSet: AttributeSet? = null) :
    MotionLayout(context, attributeSet) {
    private var motionTouched = false
    private val mainContainerView by lazy {
        findViewById<View>(R.id.main_container)
    }
    private val hitRec = Rect()
    private val gestureListener by lazy {
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                mainContainerView.getHitRect(hitRec)
                return hitRec.contains(e1.x.toInt(), e1.y.toInt())
            }
        }
    }
    private val gestureDetector by lazy {
        GestureDetector(context, gestureListener)
    }

    init {
        setTransitionListener(object : TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                motionTouched = false
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }

        })
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                motionTouched = false
                return super.onTouchEvent(event)
            }
        }
        if (!motionTouched) {
            mainContainerView.getHitRect(hitRec)
            motionTouched = hitRec.contains(event.x.toInt(), event.y.toInt())
        }

        return super.onTouchEvent(event) && motionTouched
    }

    override fun onInterceptHoverEvent(event: MotionEvent?): Boolean {
        return super.onInterceptHoverEvent(event)
    }


}