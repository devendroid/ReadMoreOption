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

package com.devs.readmoreoption;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ${Deven} on 6/1/18.
 */
public class ReadMoreOption {

    // required
    private Context context;
    // optional
    private int textLength;
    private String moreLabel;
    private String lessLabel;
    private int moreLabelColor;
    private int lessLabelColor;
    private boolean labelUnderLine;

    private ReadMoreOption(Builder builder){
        this.context = builder.context;
        this.textLength = builder.textLength;
        this.moreLabel = builder.moreLabel;
        this.lessLabel = builder.lessLabel;
        this.moreLabelColor = builder.moreLabelColor;
        this.lessLabelColor = builder.lessLabelColor;
        this.labelUnderLine = builder.labelUnderLine;
    }

    public void addReadMoreTo(final TextView textView, final String text){

        if(text.length() <= textLength) {
            textView.setText(text);
            return;
        }

        SpannableString ss = new SpannableString(text.substring(0, textLength) + "... "+ moreLabel);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                addReadLess(textView, text);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(labelUnderLine);
                ds.setColor(moreLabelColor);
            }
        };
        ss.setSpan(clickableSpan, ss.length() - moreLabel.length(), ss.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss)
        ;
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void addReadLess(final TextView textView, final String text ) {
        SpannableString ss = new SpannableString(text + " "+ lessLabel);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        addReadMoreTo(textView, text);
                    }
                });
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(labelUnderLine);
                ds.setColor(lessLabelColor);
            }
        };
        ss.setSpan(clickableSpan, ss.length() - lessLabel.length(), ss.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss)
        ;
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static class Builder {
        // required
        private Context context;
        // optional
        private int textLength = 100;
        private String moreLabel = "read more";
        private String lessLabel = "read less";
        private int moreLabelColor = Color.parseColor("#ff00ff");
        private int lessLabelColor =  Color.parseColor("#ff00ff");
        private boolean labelUnderLine = false;

        public Builder(Context context){
            this.context = context;
        }

        public Builder textLength(int length){
            this.textLength = length;
            return this;
        }

        public Builder moreLabel(String moreLabel){
            this.moreLabel = moreLabel;
            return this;
        }

        public Builder lessLabel(String lessLabel){
            this.lessLabel = lessLabel;
            return this;
        }

        public Builder moreLabelColor(int moreLabelColor){
            this.moreLabelColor = moreLabelColor;
            return this;
        }

        public Builder lessLabelColor(int lessLabelColor){
            this.lessLabelColor = lessLabelColor;
            return this;
        }

        public Builder labelUnderLine(boolean labelUnderLine){
            this.labelUnderLine = labelUnderLine;
            return this;
        }

        public ReadMoreOption build(){
            return new ReadMoreOption(this);
        }

    }

}
