/*
 *  Copyright (c) 2018 Deven.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.devs.readmoreoption

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView

class ReadMoreOption(
    // required
    var context: Context? = null,
    // optional
    val textLength: Int = 0,
    val textLengthType: Int = 0,
    val moreLabel: String? = null,
    val lessLabel: String? = null,
    val moreLabelColor: Int = 0,
    val lessLabelColor: Int = 0,
    val labelUnderLine: Boolean = false,
    val expandAnimation: Boolean = false,
) {

    private constructor(builder: Builder) : this(
        builder.context,
        builder.textLength,
        builder.textLengthType,
        builder.moreLabel,
        builder.lessLabel,
        builder.moreLabelColor,
        builder.lessLabelColor,
        builder.labelUnderLine,
        builder.expandAnimation,
    )

    fun addReadMoreTo(textView: TextView, text: CharSequence) {
        if (textLengthType == ReadMoreOption.TYPE_CHARACTER) {
            if (text.length <= textLength) {
                textView.text = text
                return
            }
        } else {
            // If TYPE_LINE
            textView.setLines(textLength)
            textView.text = text
        }
        textView.post(Runnable {
            var textLengthNew = textLength
            if (textLengthType == ReadMoreOption.TYPE_LINE) {
                if (textView.layout.lineCount <= textLength) {
                    textView.text = text
                    return@Runnable
                }
                val lp = textView.layoutParams as MarginLayoutParams
                val subString = text.toString().substring(
                    textView.layout.getLineStart(0),
                    textView.layout.getLineEnd(textLength - 1)
                )
                textLengthNew = subString.length - (moreLabel!!.length + 4 + lp.rightMargin / 6)
            }
            val spannableStringBuilder = SpannableStringBuilder(text.subSequence(0, textLengthNew))
                .append("...")
                .append(moreLabel)
            val ss = SpannableString.valueOf(spannableStringBuilder)
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    addReadLess(textView, text)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = labelUnderLine
                    ds.color = moreLabelColor
                }
            }
            ss.setSpan(
                clickableSpan,
                ss.length - moreLabel!!.length,
                ss.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && expandAnimation) {
                val layoutTransition = LayoutTransition()
                layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                (textView.parent as ViewGroup).layoutTransition = layoutTransition
            }
            textView.text = ss
            textView.movementMethod = LinkMovementMethod.getInstance()
        })
    }

    private fun addReadLess(textView: TextView, text: CharSequence) {
        textView.maxLines = Int.MAX_VALUE
        val spannableStringBuilder = SpannableStringBuilder(text)
            .append(lessLabel)
        val ss = SpannableString.valueOf(spannableStringBuilder)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Handler().post { addReadMoreTo(textView, text) }
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = labelUnderLine
                ds.color = lessLabelColor
            }
        }
        ss.setSpan(
            clickableSpan,
            ss.length - lessLabel!!.length,
            ss.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    class Builder(val c: Context) {
        // required
        val context: Context = c

        // optional
        var textLength = 100
            private set
        var textLengthType = ReadMoreOption.TYPE_CHARACTER
            private set
        var moreLabel = "read more"
            private set
        var lessLabel = "read less"
            private set
        var moreLabelColor = Color.parseColor("#ff00ff")
            private set
        var lessLabelColor = Color.parseColor("#ff00ff")
            private set
        var labelUnderLine = false
            private set
        var expandAnimation = false
            private set

        fun textLength(length: Int) = apply { textLength = length }
        fun textLengthType(type: Int) = apply { textLengthType = type }
        fun moreLabel(more: String) = apply { moreLabel = more }
        fun lessLabel(less: String) = apply { lessLabel = less }
        fun moreLabelColor(morec: Int) = apply { moreLabelColor = morec }
        fun lessLabelColor(lessc: Int) = apply { lessLabelColor = lessc }
        fun labelUnderLine(ul: Boolean) = apply { labelUnderLine = ul }
        fun expandAnimation(anim: Boolean) = apply { expandAnimation = anim }

        fun build() = ReadMoreOption(this)
    }

    companion object {
        private val TAG = ReadMoreOption::class.java.simpleName
        const val TYPE_LINE = 1
        const val TYPE_CHARACTER = 2
    }

}