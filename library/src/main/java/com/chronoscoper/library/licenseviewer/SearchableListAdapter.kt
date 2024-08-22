/*
 * Copyright 2017-2024 Koki Fukuda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chronoscoper.library.licenseviewer

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.Collections

internal class SearchableListAdapter(
    private val mContext: Context,
    private val mLicensesSource: Array<String>
) : BaseAdapter() {
    private val mQueryHandledLicenses: MutableList<String> = ArrayList()

    init {
        Collections.addAll(mQueryHandledLicenses, *mLicensesSource)
    }

    override fun getCount(): Int {
        return mQueryHandledLicenses.size
    }

    override fun getItem(position: Int): Any {
        return mQueryHandledLicenses[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
            ?: LayoutInflater.from(mContext)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        val tv = view.findViewById<View>(android.R.id.text1) as TextView
        tv.text = mQueryHandledLicenses[position]

        return tv
    }

    fun setQuery(query: String?) {
        if (TextUtils.isEmpty(query)) {
            mQueryHandledLicenses.clear()
            Collections.addAll(mQueryHandledLicenses, *mLicensesSource)
        } else {
            mQueryHandledLicenses.clear()

            for (s in mLicensesSource) {
                if (s.contains(query!!)) {
                    mQueryHandledLicenses.add(s)
                }
            }
        }
        notifyDataSetChanged()
    }

    fun getLicenseName(position: Int): String {
        return mQueryHandledLicenses[position]
    }
}
