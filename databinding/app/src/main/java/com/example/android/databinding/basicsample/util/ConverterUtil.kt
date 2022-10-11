package com.example.android.databinding.basicsample.util

/**
 * In order to show a View only when it has more than 0 likes, we pass this expression to its
 * visibilty property:
 *
 * `android:visibility="@{ConverterUtil.isZero(viewmodel.likes)}"`
 *
 * This converts "likes" (an Int) into a Boolean. See [BindingConverters] for the conversion
 * from Boolean to a visibility integer.
 */
object ConverterUtil {
    @JvmStatic
    fun isZero(number: Int): Boolean = number == 0
}