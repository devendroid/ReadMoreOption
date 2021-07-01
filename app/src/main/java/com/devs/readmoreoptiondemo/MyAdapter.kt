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
package com.devs.readmoreoptiondemo

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devs.readmoreoption.ReadMoreOption

/**
 * Created by ${Deven} on 6/2/18.
 */
internal class MyAdapter internal constructor(private val context: Context) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private val readMoreOption: ReadMoreOption

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    internal class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var mTextView: TextView

        init {
            mTextView = v.findViewById(R.id.tv)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false) as View
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position % 2 == 0) {
            readMoreOption.addReadMoreTo(
                holder.mTextView,
                Html.fromHtml(context.getString(R.string.dummy_text))
            )
        } else {
            readMoreOption.addReadMoreTo(
                holder.mTextView,
                Html.fromHtml(context.getString(R.string.dummy_text)).toString()
            )
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return 20
    }

    init {
        readMoreOption = ReadMoreOption.Builder(context)
            .build()
    }
}