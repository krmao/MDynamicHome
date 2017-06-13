package org.smartrobot.util

import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.text.TextUtils
import android.view.View

object DefaultCustomViewUtil {

    fun getBoolean(typedArray: TypedArray, index: Int, defaultValue: Boolean): Boolean {
        var result: Boolean
        try {
            val resId = typedArray.getResourceId(index, -1)
            if (resId != -1)
                result = typedArray.resources.getBoolean(resId)
            else
                result = typedArray.getBoolean(index, defaultValue)
        } catch (e: Exception) {
            e.printStackTrace()
            result = defaultValue
        }

        return result
    }

    fun getFloat(typedArray: TypedArray, index: Int, defaultValue: Float): Float {
        var result: Float
        try {
            result = typedArray.getFloat(index, defaultValue)
        } catch (e: Exception) {
            e.printStackTrace()
            result = defaultValue
        }

        return result
    }

    @Suppress("DEPRECATION")
    fun getDrawable(typedArray: TypedArray, index: Int, @ColorInt defaultColorInt: Int, @DrawableRes defaultRes: Int): Drawable? {
        var result: Drawable? = null
        try {
            val drawable = typedArray.getDrawable(index)
            if (drawable != null) {
                result = drawable
            } else {
                val color = typedArray.getColor(index, Integer.MAX_VALUE)
                if (color != Integer.MAX_VALUE) {
                    result = ColorDrawable(color)
                } else {
                    val colorStr = typedArray.getString(index)
                    if (!TextUtils.isEmpty(colorStr)) {
                        try {
                            result = ColorDrawable(Color.parseColor(colorStr))
                        } catch (ignored: Exception) {
                        }

                    } else {
                        val resId = typedArray.getResourceId(index, -1)
                        if (resId != -1) {
                            result = typedArray.resources.getDrawable(resId)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (result == null) {
            if (defaultColorInt != -1)
                result = ColorDrawable(defaultColorInt)
            if (defaultRes != -1)
                result = typedArray.resources.getDrawable(defaultRes)
        }
        return result
    }

    fun getString(typedArray: TypedArray, index: Int, defaultValue: String): String {
        var result: String? = null
        try {
            val resId = typedArray.getResourceId(index, -1)
            if (resId != -1)
                result = typedArray.resources.getString(resId)
            else
                result = typedArray.getString(index)
        } catch (e: Exception) {
            e.printStackTrace()
            result = defaultValue
        }

        if (result == null)
            result = defaultValue
        return result
    }

    /**
     * 为 view 设置背景

     * @param view         view
     * *
     * @param index        styleable
     * *
     * @param defaultColor -1 or >0
     * *
     * @param defaultRes   -1 or >0
     * *
     * @param typedArray   typedArray
     */
    fun setBackground(view: View, index: Int, defaultColor: Int, defaultRes: Int, typedArray: TypedArray) {
        try {
            if (defaultColor != -1)
                view.setBackgroundColor(defaultColor)
            if (defaultRes != -1)
                view.setBackgroundResource(defaultRes)

            val drawable = typedArray.getDrawable(index)
            if (drawable != null) {
                view.background = drawable
            } else {
                val color = typedArray.getColor(index, Integer.MAX_VALUE)
                if (color != Integer.MAX_VALUE) {
                    view.setBackgroundColor(color)
                } else {
                    val colorStr = typedArray.getString(index)
                    if (!TextUtils.isEmpty(colorStr)) {
                        try {
                            view.setBackgroundColor(Color.parseColor(colorStr))
                        } catch (ignored: Exception) {
                        }

                    } else {
                        val resId = typedArray.getResourceId(index, -1)
                        if (resId != -1) {
                            view.setBackgroundResource(resId)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
