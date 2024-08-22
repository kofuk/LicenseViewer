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
package com.chronoscoper.library.licenseviewer;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SearchableListAdapter extends BaseAdapter {
    private final Context mContext;
    private final String[] mLicensesSource;

    SearchableListAdapter(Context context, String[] licenses) {
        mContext = context;
        mLicensesSource = licenses;

        mQueryHandledLicenses = new ArrayList<>();
        Collections.addAll(mQueryHandledLicenses, licenses);
    }

    private final List<String> mQueryHandledLicenses;

    @Override
    public int getCount() {
        return mQueryHandledLicenses.size();
    }

    @Override
    public Object getItem(int position) {
        return mQueryHandledLicenses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(mContext)
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }
        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        tv.setText(mQueryHandledLicenses.get(position));

        return tv;
    }

    void setQuery(String query) {
        if (TextUtils.isEmpty(query)) {
            mQueryHandledLicenses.clear();
            Collections.addAll(mQueryHandledLicenses, mLicensesSource);
        } else {
            mQueryHandledLicenses.clear();

            for (String s : mLicensesSource) {
                if (s != null && s.contains(query)) {
                    mQueryHandledLicenses.add(s);
                }
            }
        }
        notifyDataSetChanged();
    }

    String getLicenseName(int position) {
        return mQueryHandledLicenses.get(position);
    }
}
