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

package com.devs.readmoreoptiondemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devs.readmoreoption.ReadMoreOption;

/**
 * Created by ${Deven} on 6/2/18.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private ReadMoreOption readMoreOption;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
     static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
         TextView mTextView;
         ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.tv);
        }
    }

     MyAdapter(Context context) {
        this.context = context;
        readMoreOption = new ReadMoreOption.Builder(context).textLength(3,ReadMoreOption.TYPE_LINE)
                .build();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position % 3 == 0) {
            readMoreOption.addReadMoreTo(holder.mTextView, Html.fromHtml(context.getString(R.string.dummy_html_text)));
        } else  if (position % 2 == 0) {
            readMoreOption.addReadMoreTo(holder.mTextView, Html.fromHtml(context.getString(R.string.dummy_html_text_with_new_line)).toString());
        }else{

            readMoreOption.addReadMoreTo(holder.mTextView, context.getString(R.string.dummy_normal_text_with_new_line));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 20;
    }
}