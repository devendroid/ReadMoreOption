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

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<View>(R.id.my_recycler_view) as RecyclerView
        val mLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = mLayoutManager
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            mLayoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        val mAdapter = MyAdapter(this)
        recyclerView.adapter = mAdapter


//        TextView tv = (TextView)findViewById(R.id.tv);
//        tv.setText(getString(R.string.dummy_text));
//
//        ReadMoreOption readMoreOption = new ReadMoreOption.Builder(this)
//              // Optional parameters
//                .textLength(3, ReadMoreOption.TYPE_LINE) //OR
//              //.textLength(300, ReadMoreOption.TYPE_CHARACTER)
//                .moreLabel("MORE")
//                .lessLabel("LESS")
//                .moreLabelColor(Color.RED)
//                .lessLabelColor(Color.BLUE)
//                .labelUnderLine(true)
//                .expandAnimation(true)
//                .build();
//        readMoreOption.addReadMoreTo(tv, getString(R.string.dummy_text));
    }
}